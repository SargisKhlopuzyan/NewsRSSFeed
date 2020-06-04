package app.sargis.khlopuzyan.news.rss.feed.ui.archive

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import app.sargis.khlopuzyan.news.rss.feed.R
import app.sargis.khlopuzyan.news.rss.feed.databinding.LayoutRecyclerViewItemArchiveBinding
import app.sargis.khlopuzyan.news.rss.feed.model.Item
import app.sargis.khlopuzyan.news.rss.feed.ui.common.BindableAdapter
import app.sargis.khlopuzyan.news.rss.feed.util.DataLoadingState

/**
 * Created by Sargis Khlopuzyan, on 12/18/2019.
 *
 * @author Sargis Khlopuzyan (sargis.khlopuzyan@gmail.com)
 */
class ArchiveAdapter(
    val viewModel: ArchiveViewModel
) : RecyclerView.Adapter<ArchiveAdapter.ViewHolder>(), BindableAdapter<List<Item>> {

    private var storedAlbums = mutableListOf<Item>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: LayoutRecyclerViewItemArchiveBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.layout_recycler_view_item_archive,
            parent, false
        )
        return ViewHolder(
            binding
        )
    }

    override fun getItemCount(): Int {
        return storedAlbums.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(storedAlbums[position], viewModel)
    }

    override fun setItems(items: List<Item>?) {
        storedAlbums.clear()
        items?.let {
            storedAlbums.addAll(items)
        }
        notifyDataSetChanged()
    }

    class ViewHolder(binding: LayoutRecyclerViewItemArchiveBinding) :
        RecyclerView.ViewHolder(binding.root) {

        var binding: LayoutRecyclerViewItemArchiveBinding = binding

        fun bindData(item: Item?, viewModel: ArchiveViewModel) {
            binding.viewModel = viewModel
            binding.item = item
        }
    }

}