package app.sargis.khlopuzyan.news.rss.feed.repository

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

//    suspend fun cacheNewsDetail(item: Item?): String?

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

    override suspend fun saveNewsInCache(item: Item): Long {
        item.cacheState = CacheState.Cached
        val archive = cacheNewsDetail(item)
        return if (archive.isNullOrBlank()) {
            -1
        } else {
            val id = databaseManager.saveNewsInDatabase(item)
            if (id == -1L) {
                item.cacheState = CacheState.NotCached
                deleteNewsDetailCache(item)
            }
            id
        }
    }

    override suspend fun deleteNewsFromCache(item: Item): Int =
        databaseManager.deleteNewsFromDatabase(item)

    private fun cacheNewsDetail(item: Item?): String? {
        return CacheManager.downloadFile(item?.guid, item?.guid)
    }

    private fun deleteNewsDetailCache(item: Item?): Boolean {
        //TODO
        return true
//        return CacheManager.downloadFile(item?.guid, item?.guid)
    }
}