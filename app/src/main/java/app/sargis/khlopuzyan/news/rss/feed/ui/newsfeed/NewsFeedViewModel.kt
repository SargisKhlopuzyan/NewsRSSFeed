package app.sargis.khlopuzyan.news.rss.feed.ui.newsfeed

import androidx.lifecycle.ViewModel
import app.sargis.khlopuzyan.news.rss.feed.repository.NewsRepository

class NewsFeedViewModel constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {

}