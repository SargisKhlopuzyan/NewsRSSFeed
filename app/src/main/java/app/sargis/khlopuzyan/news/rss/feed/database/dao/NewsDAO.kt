package app.sargis.khlopuzyan.news.rss.feed.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import app.sargis.khlopuzyan.news.rss.feed.model.Item

@Dao
interface NewsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNews(item: Item): Long

    @Update
    fun updateNews(vararg items: Item): Int

    @Query("DELETE FROM items WHERE guid = :id")
    fun deleteNews(id: String?): Int

    @Query("SELECT * FROM items WHERE guid = :id")
    fun getNewsById(id: String?): Item?

//    @Query("SELECT * FROM items WHERE guid LIKE :guid")
//    fun getAllArchiveNews(guid: String?): List<RssItem>

    @Query("SELECT * FROM items")
    fun getAllArchiveNews(): List<Item>

    @Query("SELECT * FROM items")

    fun getAllArchiveNewsLiveData(): LiveData<List<Item>?>

}