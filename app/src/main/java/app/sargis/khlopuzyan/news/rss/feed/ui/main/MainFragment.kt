package app.sargis.khlopuzyan.news.rss.feed.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import app.sargis.khlopuzyan.news.rss.feed.R
import app.sargis.khlopuzyan.news.rss.feed.databinding.FragmentMainBinding
import app.sargis.khlopuzyan.news.rss.feed.ui.common.DaggerFragmentX

class MainFragment : DaggerFragmentX() {

    private lateinit var binding: FragmentMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.e("LOG_TAG", "MainFragment-> onCreate")
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.supportFragmentManager?.let {
            val introViewPagerAdapter = MainPagerAdapter(it)
            binding.viewPager.adapter = introViewPagerAdapter
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = MainFragment()

    }
}