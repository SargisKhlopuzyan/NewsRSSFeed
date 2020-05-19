package app.sargis.khlopuzyan.news.rss.feed.repository

import app.sargis.khlopuzyan.news.rss.feed.database.DatabaseManager
import app.sargis.khlopuzyan.news.rss.feed.model.Item

interface NewsDetailsRepository {
    fun saveNewsInCache(item: Item): Long
    fun deleteNewsFromCache(item: Item): Int
}

/**
 * Repository implementation for doing caching queries.
 */
class NewsDetailsRepositoryImpl(
    private val databaseManager: DatabaseManager
) : NewsDetailsRepository {

    override fun saveNewsInCache(item: Item): Long {
        return databaseManager.saveNewsInDatabase(item)
    }

    override fun deleteNewsFromCache(item: Item): Int {
        return databaseManager.deleteNewsFromDatabase(item)
    }

}