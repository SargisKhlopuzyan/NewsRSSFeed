package app.sargis.khlopuzyan.news.rss.feed.database

import android.content.Context
import androidx.lifecycle.LiveData
import app.sargis.khlopuzyan.news.rss.feed.model.Item
import javax.inject.Inject

class DatabaseManager @Inject constructor(var context: Context) {

    fun saveNewsInDatabase(item: Item): Long {
        return NewsDatabase.getInstance(context).getNewsDAO().insertNews(item)
    }

    fun deleteNewsFromDatabase(item: Item): Int {
        return NewsDatabase.getInstance(context).getNewsDAO().deleteNews(item.guid)
    }

    fun getNewsFromDatabase(name: String): Item? {
        return NewsDatabase.getInstance(context).getNewsDAO().getNewsById(name)
    }

//    fun getAllMatchedNewsFromDatabase(name: String): List<Item> {
//        return NewsDatabase.getInstance(context).getNewsDAO().getAllArchiveNews(name)
//    }

    fun getAllArchiveNewsFromDatabase(): List<Item> {
        return NewsDatabase.getInstance(context).getNewsDAO().getAllArchiveNews()
    }

    fun getAllArchiveNewsLiveDataFromDatabase(): LiveData<List<Item>?> {
        return NewsDatabase.getInstance(context).getNewsDAO().getAllArchiveNewsLiveData()
    }

}