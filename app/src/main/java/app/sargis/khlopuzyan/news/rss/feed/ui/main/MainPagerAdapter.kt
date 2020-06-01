package app.sargis.khlopuzyan.news.rss.feed.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import app.sargis.khlopuzyan.news.rss.feed.ui.archive.ArchiveFragment
import app.sargis.khlopuzyan.news.rss.feed.ui.newsFeed.NewsFeedFragment

class MainPagerAdapter(fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> NewsFeedFragment.newInstance()
            1 -> ArchiveFragment.newInstance()
            else -> throw IllegalArgumentException()
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "News feed"
            1 -> "Archive"
            else -> throw IllegalArgumentException()
        }
    }
}