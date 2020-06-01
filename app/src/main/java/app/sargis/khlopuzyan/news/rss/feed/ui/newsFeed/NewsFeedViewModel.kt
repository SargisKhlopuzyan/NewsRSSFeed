package app.sargis.khlopuzyan.news.rss.feed.ui.newsFeed

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.sargis.khlopuzyan.news.rss.feed.helper.SingleLiveEvent
import app.sargis.khlopuzyan.news.rss.feed.model.Item
import app.sargis.khlopuzyan.news.rss.feed.model.NewsFeed
import app.sargis.khlopuzyan.news.rss.feed.networking.callback.Result
import app.sargis.khlopuzyan.news.rss.feed.repository.NewsFeedRepository
import app.sargis.khlopuzyan.news.rss.feed.util.CacheState
import app.sargis.khlopuzyan.news.rss.feed.util.DataLoadingState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsFeedViewModel constructor(
    private val newsFeedRepository: NewsFeedRepository
) : ViewModel() {

    val openNewsDetailLiveData: SingleLiveEvent<Item> = SingleLiveEvent()

    val newsFeedLiveData = MutableLiveData<MutableList<Item>>(mutableListOf())
    val dataLoadingStateLiveData = MutableLiveData<DataLoadingState>()
    val errorMessageLiveData = MutableLiveData<String>()

    init {
        searchMoreNews()
    }

    /**
     * Handles search result
     * */
    private fun handleSearchResult(newsFeed: NewsFeed?) {
        val items: MutableList<Item>?
        if (newsFeed?.items == null) {
            items = mutableListOf()
        } else {
            items = newsFeedLiveData.value
            items?.addAll(newsFeed.items)
        }

        newsFeedLiveData.value = items
    }

    /**
     * Handles rss list item click
     * */
    fun onItemClick(item: Item?) {
        item?.let {
            openNewsDetailLiveData.value = it
        }
    }

    /**
     * Handles caching icon click
     * */
    fun onCachingActionClick(item: Item?) {
        when (item?.cacheState) {
            CacheState.NotCached -> {
                saveItemInCache(item)
            }
            CacheState.Cached -> {
                deleteNewsFromCache(item)
            }
            else -> {
            }
        }
    }

    /**
     * Handles retry icon click
     * */
    fun retryClick(v: View) {
        searchMoreNews()
    }

    fun searchMoreNews() {

        viewModelScope.launch(Dispatchers.Main) {

            dataLoadingStateLiveData.value = DataLoadingState.Loading

            when (val resultNewsFeed = newsFeedRepository.searchNewsFeed()) {

                is Result.Success -> {
                    dataLoadingStateLiveData.value = DataLoadingState.Loaded
                    handleSearchResult(resultNewsFeed.data)
                }

                is Result.Error -> {
                    errorMessageLiveData.value =
                        "Something went wrong.\nError code: ${resultNewsFeed.errorCode}"
                    dataLoadingStateLiveData.value =
                        DataLoadingState.Failure(Exception("${resultNewsFeed.errorCode}"))
                }

                is Result.Failure -> {
                    errorMessageLiveData.value =
                        "Something went wrong.\nCheck your internet connection"
                    dataLoadingStateLiveData.value =
                        DataLoadingState.Failure(resultNewsFeed.throwable)
                }
            }
        }
    }

    /**
     * Checks weather api has pages available
     * */
    fun hasExtraRow(): Boolean =
        (dataLoadingStateLiveData.value != null && dataLoadingStateLiveData.value != DataLoadingState.Loaded) /*|| (loadedPageIndex < availablePages)*/

    /**
     * Saves item in cache
     *
     * @param item item to save in cache
     * */
    private fun saveItemInCache(item: Item) {
        viewModelScope.launch {

            var index: Int = -1

            val pref = { _index: Int, _item: Item ->
                if (_item.guid.equals(item.guid)) {
                    index = _index
                }
                _item.guid.equals(item.guid)
            }

            newsFeedLiveData.value?.let {
                it.filterIndexed(pref)
            }

            setNewsCachingState(item, index, CacheState.InProcess)

            //TODO
            val cachedFileName = newsFeedRepository.cacheNewsDetail(item)
//            newsFeedRepository.saveNewsInCache(item)

            setNewsCachingState(item, index, CacheState.Cached)

        }
    }

    /**
     * Deletes item from cache
     *
     * @param item news to delete from cache
     * */
    private fun deleteNewsFromCache(item: Item) {

        viewModelScope.launch {
            val index: Int = newsFeedLiveData.value?.indexOf(item)!!
            setNewsCachingState(item, index, CacheState.InProcess)
            newsFeedRepository.deleteTopItemFromCache(item)
            setNewsCachingState(item, index, CacheState.NotCached)
        }
    }

    /**
     * Sets item caching state
     * Uses to show caching animation
     *
     * @param item news
     * @param index index in list
     * @param cacheState cache state
     * */
    private fun setNewsCachingState(item: Item, index: Int, cacheState: CacheState) {
        val newItem = item.copy(cacheState = cacheState)
        val newItems: MutableList<Item> = mutableListOf()
        newItems.addAll(newsFeedLiveData.value!!)
        newItems[index] = newItem
        newsFeedLiveData.value = newItems
    }

//    1561984094
//    87c9xf
}