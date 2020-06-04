package app.sargis.khlopuzyan.news.rss.feed.repository

import androidx.lifecycle.LiveData
import app.sargis.khlopuzyan.news.rss.feed.database.DatabaseManager
import app.sargis.khlopuzyan.news.rss.feed.model.Item
import app.sargis.khlopuzyan.news.rss.feed.util.CacheManager

interface ArchiveRepository {
    fun getAllArchiveNewsLiveData(): LiveData<List<Item>?>
    fun deleteNewsFromCache(item: Item): Int
}

/**
 * Repository implementation for doing caching queries.
 */
class ArchiveRepositoryImpl(
    private val databaseManager: DatabaseManager
) : ArchiveRepository {

    override fun getAllArchiveNewsLiveData(): LiveData<List<Item>?> {
        return databaseManager.getAllArchiveNewsLiveDataFromDatabase()
    }

    override fun deleteNewsFromCache(item: Item): Int {
        item.guid?.let {
            val isDeletedFromDb = databaseManager.deleteNewsFromDatabase(item)
            CacheManager.deleteFile(it)
            return isDeletedFromDb
        }
        return -1
    }
}