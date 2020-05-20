package app.sargis.khlopuzyan.news.rss.feed.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import app.sargis.khlopuzyan.news.rss.feed.R
import app.sargis.khlopuzyan.news.rss.feed.databinding.FragmentMainBinding
import app.sargis.khlopuzyan.news.rss.feed.ui.common.DaggerFragmentX

class MainFragment : DaggerFragmentX() {

    private lateinit var binding: FragmentMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.e("LOG_TAG", "onCreateView 1")
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.e("LOG_TAG", "onViewCreated")


        activity?.supportFragmentManager?.let {
            Log.e("LOG_TAG", "XXXXX")
            val introViewPagerAdapter = MainPagerAdapter(it)
            binding.viewPager.adapter = introViewPagerAdapter
        }
//        val introViewPagerAdapter = MainPagerAdapter( parentFragmentManager)
//        binding.viewPager.adapter = introViewPagerAdapter
    }

    companion object {

        @JvmStatic
        fun newInstance() = MainFragment()

    }

}
