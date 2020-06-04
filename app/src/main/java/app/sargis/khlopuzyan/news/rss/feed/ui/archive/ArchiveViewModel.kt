package app.sargis.khlopuzyan.news.rss.feed.ui.archive

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.observe
import androidx.lifecycle.viewModelScope
import app.sargis.khlopuzyan.news.rss.feed.helper.SingleLiveEvent
import app.sargis.khlopuzyan.news.rss.feed.model.Item
import app.sargis.khlopuzyan.news.rss.feed.repository.ArchiveRepository
import app.sargis.khlopuzyan.news.rss.feed.util.CacheState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ArchiveViewModel constructor(
    private val archiveRepository: ArchiveRepository
) : ViewModel() {

    val openNewsDetailLiveData: SingleLiveEvent<Item> = SingleLiveEvent()

    var archiveNewsLiveData = archiveRepository.getAllArchiveNewsLiveData()

    /**
     * Handles rss list item click
     * */
    fun onItemClick(item: Item?) {
        item?.let {
            openNewsDetailLiveData.value = it
        }
    }

    /**
     * Handles archive list item click
     * */
    fun onCachingActionClick(item: Item) {
        viewModelScope.launch(Dispatchers.Main) {
            item.cacheState = CacheState.NotCached
            archiveRepository.deleteNewsFromCache(item)
        }
    }
}