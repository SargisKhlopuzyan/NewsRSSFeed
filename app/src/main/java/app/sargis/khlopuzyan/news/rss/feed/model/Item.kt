package app.sargis.khlopuzyan.news.rss.feed.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "items")
@Parcelize
@JsonClass(generateAdapter = true)
data class Item(

    @PrimaryKey(autoGenerate = true)
    var dbRowId: Long = 0.toLong(),

    @Json(name = "author")
    val author: String,

    // TODO Any
    @Json(name = "categories")
    val categories: List<String> = listOf(),

    @Json(name = "content")
    val content: String,

    @Json(name = "description")
    val description: String,

    @Json(name = "enclosure")
    val enclosure: Enclosure?,

    @Json(name = "guid")
    val guid: String,

    @Json(name = "link")
    val link: String,

    @Json(name = "pubDate")
    val pubDate: String,

    @Json(name = "thumbnail")
    val thumbnail: String,

    @Json(name = "title")
    val title: String

) : Parcelable