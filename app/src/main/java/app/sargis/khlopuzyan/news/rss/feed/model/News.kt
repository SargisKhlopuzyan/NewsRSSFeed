package app.sargis.khlopuzyan.news.rss.feed.model

import com.squareup.moshi.Json

data class News(

    @Json(name = "feed")
    val feed: Feed,

    @Json(name = "items")
    val items: List<Item>,

    @Json(name = "status")
    val status: String
)