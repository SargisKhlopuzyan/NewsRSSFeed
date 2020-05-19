package app.sargis.khlopuzyan.news.rss.feed.ui.newsFeed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import app.sargis.khlopuzyan.news.rss.feed.R
import app.sargis.khlopuzyan.news.rss.feed.databinding.FragmentNewsfeedBinding
import app.sargis.khlopuzyan.news.rss.feed.ui.common.DaggerFragmentX
import javax.inject.Inject

class NewsFeedFragment : DaggerFragmentX() {

    companion object {

        private const val ARG_X = "arg_x"

        fun newInstance(/*album: Album*/) = NewsFeedFragment().apply {
            arguments = Bundle().apply {
//                putParcelable(ARG_X, album)
            }
        }
    }

    @Inject
    lateinit var newsFeedViewModel: NewsFeedViewModel

    private lateinit var binding: FragmentNewsfeedBinding

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
            DataBindingUtil.inflate(inflater, R.layout.fragment_newsfeed, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.viewModel = newsFeedViewModel
    }

}