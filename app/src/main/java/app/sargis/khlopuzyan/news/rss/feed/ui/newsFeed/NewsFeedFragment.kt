package app.sargis.khlopuzyan.news.rss.feed.ui.newsFeed

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import app.sargis.khlopuzyan.news.rss.feed.R
import app.sargis.khlopuzyan.news.rss.feed.databinding.FragmentNewsfeedBinding
import app.sargis.khlopuzyan.news.rss.feed.model.Item
import app.sargis.khlopuzyan.news.rss.feed.ui.common.DaggerFragmentX
import app.sargis.khlopuzyan.news.rss.feed.ui.newsDetails.NewsDetailsFragment
import javax.inject.Inject


class NewsFeedFragment : DaggerFragmentX() {

    companion object {

        private const val ARG_X = "arg_x"

        fun newInstance() = NewsFeedFragment().apply {
            arguments = Bundle().apply {
                Log.e("LOG_TAG", "NewsFeedFragment -> newInstance")
            }
        }
    }

    @Inject
    lateinit var viewModel: NewsFeedViewModel

    private lateinit var binding: FragmentNewsfeedBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_newsfeed, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        Log.e("LOG_TAG", "NewsFeedFragment -> onCreateView")
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.viewModel = viewModel

        Log.e("LOG_TAG", "NewsFeedFragment -> onActivityCreated")

        setupRecyclerView()
        setupObservers()
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(context)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.hasFixedSize()

        val adapter = NewsFeedAdapter(
            viewModel
        )
        adapter.setHasStableIds(true)
        binding.recyclerView.adapter = adapter
    }

    private fun setupObservers() {

        viewModel.openNewsDetailLiveData.observe(viewLifecycleOwner) {
            openNewsDetailFragment(it)
        }

//        viewModel.showToastLiveData.observe(this) {
//            Snackbar.make(binding.toolbar, "$it", Snackbar.LENGTH_SHORT)
//                .show()
//        }

    }

    private fun openNewsDetailFragment(
        item: Item
    ) {
        activity?.supportFragmentManager?.commit {
            replace(
                android.R.id.content,
                NewsDetailsFragment.newInstance(item),
                "fragment_news_details"
            )
            addToBackStack("news_details")
        }
    }
}