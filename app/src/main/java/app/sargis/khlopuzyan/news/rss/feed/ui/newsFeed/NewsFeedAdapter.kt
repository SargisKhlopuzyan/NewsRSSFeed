package app.sargis.khlopuzyan.news.rss.feed.ui.newsFeed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import app.sargis.khlopuzyan.news.rss.feed.R
import app.sargis.khlopuzyan.news.rss.feed.databinding.LayoutRecyclerViewItemLoadingBinding
import app.sargis.khlopuzyan.news.rss.feed.databinding.LayoutRecyclerViewItemNetworkErrorBinding
import app.sargis.khlopuzyan.news.rss.feed.databinding.LayoutRecyclerViewItemNewsFeedBinding
import app.sargis.khlopuzyan.news.rss.feed.model.Item
import app.sargis.khlopuzyan.news.rss.feed.ui.common.BindableAdapter
import app.sargis.khlopuzyan.news.rss.feed.util.DataLoadingState

class NewsFeedAdapter(
    val viewModel: NewsFeedViewModel
) : ListAdapter<Item?, RecyclerView.ViewHolder>(DiffCallback()), BindableAdapter<List<Item>> {

    private var dataLoadingState: DataLoadingState? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {

            R.layout.layout_recycler_view_item_news_feed -> {

                val binding: LayoutRecyclerViewItemNewsFeedBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.layout_recycler_view_item_news_feed,
                    parent, false
                )
                ItemViewHolder(binding)
            }

            R.layout.layout_recycler_view_item_loading -> {
                val binding: LayoutRecyclerViewItemLoadingBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.layout_recycler_view_item_loading,
                    parent, false
                )
                LoadingViewHolder(binding)
            }

            R.layout.layout_recycler_view_item_network_error -> {

                val binding: LayoutRecyclerViewItemNetworkErrorBinding =
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.layout_recycler_view_item_network_error,
                        parent, false
                    )
                NetworkErrorViewHolder(binding)
            }

            else -> throw IllegalArgumentException("Unknown view type $viewType")
        }
    }

    override fun getItemCount(): Int {

        if (dataLoadingState == null) {
            return 0
        }

        return if (viewModel.hasExtraRow()) {
            super.getItemCount() + 1
        } else {
            super.getItemCount()
        }
    }

    override fun getItemId(position: Int) = position.toLong()


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (getItemViewType(position)) {

            R.layout.layout_recycler_view_item_news_feed -> {
                if (dataLoadingState is DataLoadingState.Failure && (itemCount > 1) && (position == itemCount - 2)) {
                    viewModel.dataLoadingStateLiveData.value = DataLoadingState.Loaded
                }
                (holder as ItemViewHolder).bindItem(getItem(position), viewModel)
            }

            R.layout.layout_recycler_view_item_loading -> {
                if (dataLoadingState == DataLoadingState.Loaded && viewModel.hasExtraRow()) {
                    viewModel.searchMoreNews()
                }
            }

            R.layout.layout_recycler_view_item_network_error -> {
                (holder as NetworkErrorViewHolder).bind(viewModel)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {

        return when (dataLoadingState) {

            is DataLoadingState.Loaded, is DataLoadingState.Loading -> {
                if (viewModel.hasExtraRow() && position == itemCount - 1) {
                    R.layout.layout_recycler_view_item_loading
                } else {
                    R.layout.layout_recycler_view_item_news_feed
                }
            }

            is DataLoadingState.Failure -> {
                if (viewModel.hasExtraRow() && position == itemCount - 1) {
                    R.layout.layout_recycler_view_item_network_error
                } else {
                    R.layout.layout_recycler_view_item_news_feed
                }
            }

            else -> throw IllegalArgumentException("Unknown view type")
        }
    }

    override fun setItems(items: List<Item>?) {
        if (items != null) {
            submitList(items)
        } else {
            submitList(listOf())
        }
    }

    override fun setDataLoadingState(newDataLoadingState: DataLoadingState?) {
        dataLoadingState = newDataLoadingState
        val pos = if (itemCount > 0) itemCount - 1 else 0
        notifyItemChanged(pos)
    }

    // ViewHolder

    class ItemViewHolder(private val binding: LayoutRecyclerViewItemNewsFeedBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItem(item: Item?, viewModel: NewsFeedViewModel) {
            binding.viewModel = viewModel
            binding.item = item
        }
    }

    class LoadingViewHolder(binding: LayoutRecyclerViewItemLoadingBinding) :
        RecyclerView.ViewHolder(binding.root)

    class NetworkErrorViewHolder(binding: LayoutRecyclerViewItemNetworkErrorBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val binding: LayoutRecyclerViewItemNetworkErrorBinding = binding

        fun bind(viewModel: ViewModel) {
            binding.viewModel = viewModel as NewsFeedViewModel
        }
    }

}

//Callback

class DiffCallback : DiffUtil.ItemCallback<Item?>() {

    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.guid == newItem.guid
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem == newItem
    }

}