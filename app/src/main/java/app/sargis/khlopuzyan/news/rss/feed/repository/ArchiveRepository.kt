package app.sargis.khlopuzyan.news.rss.feed.repository

import androidx.lifecycle.LiveData
import app.sargis.khlopuzyan.news.rss.feed.database.DatabaseManager
import app.sargis.khlopuzyan.news.rss.feed.model.Item

interface ArchiveRepository {

    fun getAllArchiveNewsLiveData(): LiveData<List<Item>?>

    suspend fun deleteNewsFromCache(item: Item): Int
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

    override suspend fun deleteNewsFromCache(item: Item): Int =
        databaseManager.deleteNewsFromDatabase(item)

}