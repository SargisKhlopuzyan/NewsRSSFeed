package app.sargis.khlopuzyan.news.rss.feed.ui.newsFeed

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.checkSelfPermission
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
            arguments = Bundle().apply {}
        }
    }

    @Inject
    lateinit var viewModel: NewsFeedViewModel

    private lateinit var binding: FragmentNewsfeedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.viewModel = viewModel

        setupRecyclerView()
        setupObservers()

        checkPermissions()
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

    private fun checkPermissions(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(requireActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                true
            } else { ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE), 1)
                false
            }

        } else {
            //permission is automatically granted on sdk<23 upon installation
            true
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