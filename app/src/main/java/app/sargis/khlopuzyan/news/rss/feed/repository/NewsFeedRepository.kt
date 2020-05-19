package app.sargis.khlopuzyan.news.rss.feed.repository

import app.sargis.khlopuzyan.news.rss.feed.database.DatabaseManager
import app.sargis.khlopuzyan.news.rss.feed.model.News
import app.sargis.khlopuzyan.news.rss.feed.networking.api.ApiService

import app.sargis.khlopuzyan.news.rss.feed.networking.callback.Result
import app.sargis.khlopuzyan.news.rss.feed.networking.helper.getResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext

interface NewsFeedRepository {
    suspend fun searchNews(): Result<News>
}

/**
 * Repository implementation for doing news search
 */
class NewsFeedRepositoryImpl(
    private val apiService: ApiService,
    private val databaseManager: DatabaseManager,
    private val coroutineScope: CoroutineScope
) : NewsFeedRepository {

    override suspend fun searchNews(): Result<News> =
        withContext(coroutineScope.coroutineContext) {
            try {
                return@withContext apiService.searchNews()
                    .getResult()
            } catch (ex: Exception) {
                return@withContext Result.Failure(ex)
            }
        }

}