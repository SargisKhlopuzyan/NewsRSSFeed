package app.sargis.khlopuzyan.news.rss.feed.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import app.sargis.khlopuzyan.news.rss.feed.database.converter.CategoriesConverter
import app.sargis.khlopuzyan.news.rss.feed.database.converter.EnclosureConverter
import app.sargis.khlopuzyan.news.rss.feed.database.dao.NewsDAO
import app.sargis.khlopuzyan.news.rss.feed.model.Item

@TypeConverters(
    EnclosureConverter::class,
    CategoriesConverter::class
)
@Database(entities = [Item::class], version = 1, exportSchema = false)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun getNewsDAO(): NewsDAO

    companion object {

        @Volatile
        private var INSTANCE: NewsDatabase? = null

        fun getInstance(context: Context): NewsDatabase {

            if (INSTANCE == null) {

                synchronized(NewsDatabase::class) {

                    if (INSTANCE == null) {

                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            NewsDatabase::class.java, "news.db"
                        )
                            .allowMainThreadQueries()
                            .build()
                    }
                }
            }
            return INSTANCE!!
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}