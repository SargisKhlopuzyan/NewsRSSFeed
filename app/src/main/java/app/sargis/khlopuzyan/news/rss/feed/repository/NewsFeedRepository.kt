package app.sargis.khlopuzyan.news.rss.feed.repository

import android.util.Log
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

    suspend fun deleteNewsFromCache(item: Item): Int

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

        val item = _item.copy()
        item.cacheState = CacheState.Cached
        val newDetails = CacheManager.downloadFile(item.guid, item.guid)

        return if (newDetails.isNullOrBlank()) {
            -1
        } else {
            val id = databaseManager.saveNewsInDatabase(item)
            //TODO
            if (id == -1L) {
                item.cacheState = CacheState.NotCached

                val isDeleted = CacheManager.deleteFile(item.guid, item.guid)
                Log.e("LOG_TAG", "isDeleted: $isDeleted")
            }
            id
        }
    }

    override suspend fun deleteNewsFromCache(item: Item): Int {

        val isDeletedFromDb = databaseManager.deleteNewsFromDatabase(item)

        val isDeleted = CacheManager.deleteFile(item.guid, item.guid)
        Log.e("LOG_TAG", "isDeletedFromDb: $isDeletedFromDb  ||  isDeleted: $isDeleted")

        return isDeletedFromDb
    }

}