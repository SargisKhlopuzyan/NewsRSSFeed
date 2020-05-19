package app.sargis.khlopuzyan.news.rss.feed.repository

import app.sargis.khlopuzyan.news.rss.feed.database.DatabaseManager
import app.sargis.khlopuzyan.news.rss.feed.model.Item

interface NewsDetailsRepository {
    fun saveNewsInCache(album: Item): Long
    fun deleteNewsFromCache(album: Item): Int
}

/**
 * Repository implementation for doing caching queries.
 */
class NewsDetailsRepositoryImpl(
    private val databaseManager: DatabaseManager
) : NewsDetailsRepository {

    override fun saveNewsInCache(album: Item): Long {
        return databaseManager.saveNewsInDatabase(album)
    }

    override fun deleteNewsFromCache(album: Item): Int {
        return databaseManager.deleteNewsFromDatabase(album)
    }

}