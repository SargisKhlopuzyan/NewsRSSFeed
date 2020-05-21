package app.sargis.khlopuzyan.news.rss.feed.ui.newsDetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.sargis.khlopuzyan.news.rss.feed.model.Item
import app.sargis.khlopuzyan.news.rss.feed.repository.NewsDetailsRepository

class NewsDetailsViewModel constructor(
    private val newsDetailsRepository: NewsDetailsRepository
) : ViewModel() {

    val itemLiveData = MutableLiveData<Item>()

    fun setItem(item: Item) {
        itemLiveData.value = item
    }

}