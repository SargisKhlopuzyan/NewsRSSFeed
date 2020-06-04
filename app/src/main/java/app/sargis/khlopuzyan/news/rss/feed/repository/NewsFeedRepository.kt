package app.sargis.khlopuzyan.news.rss.feed.repository

import androidx.lifecycle.LiveData
import app.sargis.khlopuzyan.news.rss.feed.database.DatabaseManager
import app.sargis.khlopuzyan.news.rss.feed.model.Item
import app.sargis.khlopuzyan.news.rss.feed.model.NewsFeed
import app.sargis.khlopuzyan.news.rss.feed.networking.api.ApiService
import app.sargis.khlopuzyan.news.rss.feed.networking.callback.Result
import app.sargis.khlopuzyan.news.rss.feed.networking.helper.getResult
import app.sargis.khlopuzyan.news.rss.feed.util.CacheManager
import app.sargis.khlopuzyan.news.rss.feed.util.CacheState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext

interface NewsFeedRepository {
    suspend fun searchNewsFeed(): Result<NewsFeed>
    suspend fun saveNewsInCache(item: Item): Long

    fun deleteNewsFromCache(item: Item): Int
    fun getAllArchiveNewsLiveData(): LiveData<List<Item>?>
}

/**
 * Repository implementation for doing news search
 */
class NewsFeedRepositoryImpl(
    private val apiService: ApiService,
    private val databaseManager: DatabaseManager,
    private val coroutineScope: CoroutineScope
) : NewsFeedRepository {

    override suspend fun searchNewsFeed(): Result<NewsFeed> =
        withContext(coroutineScope.coroutineContext) {
            try {
                return@withContext apiService.searchNewsFeed()
                    .getResult()
            } catch (ex: Exception) {
                return@withContext Result.Failure(ex)
            }
        }

    override suspend fun saveNewsInCache(_item: Item): Long {
        _item.guid?.let {
            val item = _item.copy()
            item.cacheState = CacheState.Cached
            val newDetails = CacheManager.downloadFile(it, item.guid)

            return if (newDetails.isNullOrBlank()) {
                -1
            } else {
                val id = databaseManager.saveNewsInDatabase(item)
                if (id == -1L) {
                    item.cacheState = CacheState.NotCached
                    CacheManager.deleteFile(it)
                }
                id
            }
        }
        return -1
    }

    override fun deleteNewsFromCache(item: Item): Int {
        item.guid?.let {
            val isDeletedFromDb = databaseManager.deleteNewsFromDatabase(item)
            CacheManager.deleteFile(it)
            return isDeletedFromDb
        }
        return -1
    }

    override fun getAllArchiveNewsLiveData(): LiveData<List<Item>?> {
        return databaseManager.getAllArchiveNewsLiveDataFromDatabase()
    }

}