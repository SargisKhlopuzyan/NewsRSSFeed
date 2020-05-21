package app.sargis.khlopuzyan.news.rss.feed.database.converter

import androidx.room.TypeConverter
import app.sargis.khlopuzyan.news.rss.feed.util.CacheState
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CacheStateConverter {

    private val gson = Gson()

    @TypeConverter
    fun stringToCacheState(data: String?): CacheState {

        if (data == null) {
            return CacheState.NotCached
        }

        val type = object : TypeToken<CacheState>() {}.type

        return gson.fromJson<CacheState>(data, type)
    }

    @TypeConverter
    fun cacheStateToString(someObjects: CacheState): String {
        return gson.toJson(someObjects)
    }

}