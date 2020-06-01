package app.sargis.khlopuzyan.news.rss.feed.ui.newsDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import app.sargis.khlopuzyan.news.rss.feed.R
import app.sargis.khlopuzyan.news.rss.feed.databinding.FragmentNewsDetailsBinding
import app.sargis.khlopuzyan.news.rss.feed.model.Item
import app.sargis.khlopuzyan.news.rss.feed.ui.common.DaggerFragmentX
import javax.inject.Inject


class NewsDetailsFragment : DaggerFragmentX() {

    companion object {

        private const val ARG_ITEM = "arg_item"

        fun newInstance(item: Item) = NewsDetailsFragment().apply {
            arguments = Bundle().apply {
                putParcelable(ARG_ITEM, item)
            }
        }
    }

    @Inject
    lateinit var viewModel: NewsDetailsViewModel

    private lateinit var binding: FragmentNewsDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val item: Item? = arguments?.getParcelable(ARG_ITEM)
        item?.let {
            viewModel.setItem(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_news_details, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        // Enable javascript
//        binding.webView.settings.javaScriptEnabled = true
        binding.webView.webViewClient = WebViewClient()


        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.viewModel = viewModel
    }

}