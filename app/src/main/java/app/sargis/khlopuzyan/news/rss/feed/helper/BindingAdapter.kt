package app.sargis.khlopuzyan.news.rss.feed.helper

import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View
import android.webkit.WebView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import app.sargis.khlopuzyan.news.rss.feed.R
import app.sargis.khlopuzyan.news.rss.feed.model.Item
import app.sargis.khlopuzyan.news.rss.feed.ui.common.BindableAdapter
import app.sargis.khlopuzyan.news.rss.feed.util.CacheManager
import app.sargis.khlopuzyan.news.rss.feed.util.CacheState
import app.sargis.khlopuzyan.news.rss.feed.util.DataLoadingState
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target

@BindingAdapter("data")
fun <T> RecyclerView.setRecyclerViewData(items: T?) {
    if (adapter is BindableAdapter<*>) {
        @Suppress("UNCHECKED_CAST")
        (adapter as BindableAdapter<T>).setItems(items)
    }
}

@BindingAdapter("loadDataFromCacheOrUrl")
fun WebView.loadDataFromCacheOrUrl(item: Item?) {
    val cache = CacheManager.readFileContentFromInternalStorage(item?.guid)

    if (cache.isNullOrEmpty()) {
        this.loadUrl(item?.guid)
    } else {
        this.settings.javaScriptEnabled = true
        this.loadDataWithBaseURL(null, cache, "text/html", "utf-8", null)
    }
}

@BindingAdapter("content")
fun TextView.setContent(item: Item?) {
    val content = item?.content?.substringBefore("<div class=")
    this.text = content
}

@BindingAdapter("setImageResource")
fun ImageView.setImageResource(resource: String?) {

    val placeholderId: Int = R.drawable.ic_news

    if (resource == null || resource.isBlank()) {
        setImageResource(placeholderId)
        return
    }

    Glide.with(this.context)
        .load(resource)
        .apply(RequestOptions().dontTransform())
        .listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                if (context !is AppCompatActivity) return false
                val activity = context as AppCompatActivity
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    activity.startPostponedEnterTransition()
                }
                activity.supportStartPostponedEnterTransition()
                return false
            }

        })
        .fitCenter()
        .into(this)
}

@BindingAdapter("setItemCacheState")
fun LottieAnimationView.setItemCacheState(item: Item?) {
    when (item?.cacheState) {
        CacheState.Cached -> {
            repeatCount = LottieDrawable.INFINITE
            setImageResource(R.drawable.ic_saved)
        }
        CacheState.InProcess -> {
            repeatCount = LottieDrawable.RESTART
            setAnimation("loading.json")
            playAnimation()
        }
        CacheState.NotCached -> {
            repeatCount = LottieDrawable.INFINITE
            setImageResource(R.drawable.ic_save)
        }
    }
}

@BindingAdapter("isEnabled")
fun SwipeRefreshLayout.isEnabled(dataLoadingState: DataLoadingState?) {
    this.isRefreshing = false
    this.isEnabled = dataLoadingState != null && dataLoadingState is DataLoadingState.Loaded
}

@BindingAdapter("errorVisibility")
fun LinearLayout.errorVisibility(dataLoadingState: DataLoadingState?) {
    if (dataLoadingState != null && dataLoadingState is DataLoadingState.Failure) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.GONE
    }
}

@BindingAdapter("loadingVisibility")
fun LinearLayout.loadingVisibility(dataLoadingState: DataLoadingState?) {
    if (dataLoadingState != null && dataLoadingState is DataLoadingState.Loading) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.GONE
    }
}