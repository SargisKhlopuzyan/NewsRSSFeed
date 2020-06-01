package app.sargis.khlopuzyan.news.rss.feed.repository

import app.sargis.khlopuzyan.news.rss.feed.database.DatabaseManager
import app.sargis.khlopuzyan.news.rss.feed.model.Item
import app.sargis.khlopuzyan.news.rss.feed.util.CacheManager

interface NewsDetailsRepository {
    fun saveNewsInCache(item: Item): Long
    fun loadNewsFromCache(item: Item): String?
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

    override fun loadNewsFromCache(item: Item): String? {
        return CacheManager.readFileContentFromInternalStorage(item.guid)
    }

    override fun deleteNewsFromCache(item: Item): Int {
        return databaseManager.deleteNewsFromDatabase(item)
    }

}