package app.sargis.khlopuzyan.news.rss.feed.repository

import android.content.Context
import android.os.StrictMode
import android.util.Log
import app.sargis.khlopuzyan.news.rss.feed.App
import app.sargis.khlopuzyan.news.rss.feed.database.DatabaseManager
import app.sargis.khlopuzyan.news.rss.feed.model.Item
import app.sargis.khlopuzyan.news.rss.feed.model.NewsFeed
import app.sargis.khlopuzyan.news.rss.feed.networking.api.ApiService
import app.sargis.khlopuzyan.news.rss.feed.networking.callback.Result
import app.sargis.khlopuzyan.news.rss.feed.networking.helper.getResult
import app.sargis.khlopuzyan.news.rss.feed.util.CacheManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

interface NewsFeedRepository {

    suspend fun searchNewsFeed(): Result<NewsFeed>

    suspend fun cacheNewsDetail(item: Item?): String?

    suspend fun saveNewsInCache(item: Item): Long

    suspend fun deleteTopItemFromCache(item: Item): Int

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

    override suspend fun cacheNewsDetail(item: Item?): String? {
        return CacheManager.downloadFile(item?.guid, item?.guid)
    }

    override suspend fun saveNewsInCache(item: Item): Long {
        return databaseManager.saveNewsInDatabase(item)
    }

    override suspend fun deleteTopItemFromCache(item: Item): Int =
        databaseManager.deleteNewsFromDatabase(item)

}