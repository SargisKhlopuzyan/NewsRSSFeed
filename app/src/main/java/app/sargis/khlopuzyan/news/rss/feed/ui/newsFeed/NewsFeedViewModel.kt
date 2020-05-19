package app.sargis.khlopuzyan.news.rss.feed.ui.newsFeed

import androidx.lifecycle.ViewModel
import app.sargis.khlopuzyan.news.rss.feed.repository.NewsFeedRepository

class NewsFeedViewModel constructor(
    private val newsFeedRepository: NewsFeedRepository
) : ViewModel() {

}