package app.sargis.khlopuzyan.news.rss.feed.database.converter

import androidx.room.TypeConverter
import app.sargis.khlopuzyan.news.rss.feed.model.Enclosure
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class EnclosureConverter {

    private val gson = Gson()

    @TypeConverter
    fun stringToArtist(data: String?): Enclosure {

        if (data == null) {
            return Enclosure()
        }

        val type = object : TypeToken<Enclosure>() {}.type

        return gson.fromJson<Enclosure>(data, type)
    }

    @TypeConverter
    fun listToString(someObjects: Enclosure): String {
        return gson.toJson(someObjects)
    }

}