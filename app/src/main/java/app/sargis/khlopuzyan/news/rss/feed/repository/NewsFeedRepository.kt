package app.sargis.khlopuzyan.news.rss.feed.repository

import app.sargis.khlopuzyan.news.rss.feed.database.DatabaseManager
import app.sargis.khlopuzyan.news.rss.feed.model.Item
import app.sargis.khlopuzyan.news.rss.feed.model.NewsFeed
import app.sargis.khlopuzyan.news.rss.feed.networking.api.ApiService

import app.sargis.khlopuzyan.news.rss.feed.networking.callback.Result
import app.sargis.khlopuzyan.news.rss.feed.networking.helper.getResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.NonCancellable.isCancelled
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL

interface NewsFeedRepository {

    suspend fun searchNewsFeed(): Result<NewsFeed>

    suspend fun cacheNewsDetail(): Boolean

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

    override suspend fun cacheNewsDetail(): Boolean {
        // TODO
        return true
    }


    suspend fun downloadFile(url: String, downloadFile: File, downloadProgressFun: (bytesRead: Long, contentLength: Long, isDone: Boolean) -> Unit) {

//        async(CommonPool) {
//
//            val request = with(Request.Builder()) {
//                url(url)
//            }.build()
//
//            val client = with(OkHttpClient.Builder()) {
//                addNetworkInterceptor { chain ->
//                    val originalResponse = chain.proceed(chain.request())
//                    val responseBody = originalResponse.body
//
//                    responseBody?.let {
//                        originalResponse.newBuilder().body(ProgressResponseBody(it, downloadProgressFun)).build()
//                    }
//
//                }
//            }.build()
//
//            try {
//                val execute = client.newCall(request).execute()
//                val outputStream = FileOutputStream(downloadFile)
//
//                val body = execute.body
//                body?.let {
//                    with(outputStream) {
//                        write(body.bytes())
//                        close()
//                    }
//                }
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }
    }

    override suspend fun saveNewsInCache(item: Item): Long {
        return databaseManager.saveNewsInDatabase(item)
    }

    override suspend fun deleteTopItemFromCache(item: Item): Int =
        databaseManager.deleteNewsFromDatabase(item)

}