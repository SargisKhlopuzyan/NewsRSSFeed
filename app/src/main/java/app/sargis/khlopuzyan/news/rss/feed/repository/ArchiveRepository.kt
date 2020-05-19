package app.sargis.khlopuzyan.news.rss.feed.repository

import androidx.lifecycle.LiveData
import app.sargis.khlopuzyan.news.rss.feed.database.DatabaseManager
import app.sargis.khlopuzyan.news.rss.feed.model.Item

interface ArchiveRepository {
    fun getAllCachedNewsLiveData(): LiveData<List<Item>?>
}

/**
 * Repository implementation for doing caching queries.
 */
class ArchiveRepositoryImpl(
    private val databaseManager: DatabaseManager
) : ArchiveRepository {

    override fun getAllCachedNewsLiveData(): LiveData<List<Item>?> {
        return databaseManager.getAllArchiveNewsLiveDataFromDatabase()
    }

}