package app.sargis.khlopuzyan.news.rss.feed.ui.newsFeed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import app.sargis.khlopuzyan.news.rss.feed.R
import app.sargis.khlopuzyan.news.rss.feed.databinding.LayoutRecyclerViewItemNewsFeedBinding
import app.sargis.khlopuzyan.news.rss.feed.model.Item
import app.sargis.khlopuzyan.news.rss.feed.ui.common.BindableAdapter

class NewsFeedAdapter(
    val viewModel: NewsFeedViewModel
) : ListAdapter<Item?, RecyclerView.ViewHolder>(DiffCallback()), BindableAdapter<List<Item>> {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding: LayoutRecyclerViewItemNewsFeedBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.layout_recycler_view_item_news_feed,
            parent, false
        )
        return ItemViewHolder(binding)
    }

    override fun getItemId(position: Int) = position.toLong()

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ItemViewHolder).bindItem(getItem(position), viewModel)
    }

    override fun setItems(items: List<Item>?) {
        if (items != null) {
            submitList(items)
        } else {
            submitList(listOf())
        }
        notifyDataSetChanged()
    }

    // ViewHolder

    class ItemViewHolder(private val binding: LayoutRecyclerViewItemNewsFeedBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItem(item: Item?, viewModel: NewsFeedViewModel) {
            binding.viewModel = viewModel
            binding.item = item
        }
    }
}

//Callback

class DiffCallback : DiffUtil.ItemCallback<Item?>() {

    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem === newItem && oldItem.guid == newItem.guid && oldItem.cacheState == newItem.cacheState
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem == newItem
    }

}