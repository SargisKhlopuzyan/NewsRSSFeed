package app.sargis.khlopuzyan.news.rss.feed.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import app.sargis.khlopuzyan.news.rss.feed.util.CacheState
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
data class NewsFeed(

    @Json(name = "feed")
    val feed: Feed,

    @Json(name = "items")
    val items: List<Item>,

    @Json(name = "status")
    val status: String

)

@JsonClass(generateAdapter = true)
data class Feed(

    @Json(name = "author")
    val author: String? = null,

    @Json(name = "description")
    val description: String? = null,

    @Json(name = "image")
    val image: String? = null,

    @Json(name = "link")
    val link: String? = null,

    @Json(name = "title")
    val title: String? = null,

    @Json(name = "url")
    val url: String? = null

)

@Entity(tableName = "items")
@Parcelize
@JsonClass(generateAdapter = true)
data class Item(

    @PrimaryKey(autoGenerate = true)
    var dbRowId: Long = 0.toLong(),

    var cacheState: CacheState = CacheState.NotCached,

    @Json(name = "author")
    val author: String? = null,

    // TODO Any
    @Json(name = "categories")
    val categories: List<String> = listOf(),

    @Json(name = "content")
    val content: String? = null,

    @Json(name = "description")
    var description: String? = null,

    @Json(name = "enclosure")
    val enclosure: Enclosure? = null,

    @Json(name = "guid")
    val guid: String? = null,

    @Json(name = "link")
    var link: String? = null,

    @Json(name = "pubDate")
    var pubDate: String? = null,

    @Json(name = "thumbnail")
    val thumbnail: String? = null,

    @Json(name = "title")
    var title: String? = null

) : Parcelable {
    override fun equals(other: Any?): Boolean {
        return other != null && other is Item && other.guid == guid
    }
}

@JsonClass(generateAdapter = true)
@Parcelize
data class Enclosure(

    @Json(name = "link")
    val link: String? = null,

    @Json(name = "type")
    val type: String? = null

) : Parcelable