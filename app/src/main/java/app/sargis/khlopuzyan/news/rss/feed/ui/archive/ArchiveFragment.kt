package app.sargis.khlopuzyan.news.rss.feed.ui.archive

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import app.sargis.khlopuzyan.news.rss.feed.R
import app.sargis.khlopuzyan.news.rss.feed.databinding.FragmentArchiveBinding
import app.sargis.khlopuzyan.news.rss.feed.model.Item
import app.sargis.khlopuzyan.news.rss.feed.ui.common.DaggerFragmentX
import app.sargis.khlopuzyan.news.rss.feed.ui.newsDetails.NewsDetailsFragment
import javax.inject.Inject

class ArchiveFragment : DaggerFragmentX() {

    companion object {

        private const val ARG_X = "arg_x"

        fun newInstance(/*album: Album*/) = ArchiveFragment().apply {
            arguments = Bundle().apply {
//                putParcelable(ARG_X, album)
            }
        }
    }

    @Inject
    lateinit var viewModel: ArchiveViewModel

    private lateinit var binding: FragmentArchiveBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val album: Album? = arguments?.getParcelable(ARG_ALBUM)
//        viewModel.setAlbum(album)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_archive, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.viewModel = viewModel

        setupRecyclerView()
        setupObservers()
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(context)

        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.hasFixedSize()
        val adapter = ArchiveAdapter(
            viewModel
        )
        binding.recyclerView.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.openNewsDetailLiveData.observe(viewLifecycleOwner) {
            openNewsDetailFragment(it)
        }
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
