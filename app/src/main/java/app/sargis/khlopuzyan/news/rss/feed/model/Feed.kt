package app.sargis.khlopuzyan.news.rss.feed.model

import com.squareup.moshi.Json

data class Feed(

    @Json(name = "author")
    val author: String,

    @Json(name = "description")
    val description: String,

    @Json(name = "image")
    val image: String,

    @Json(name = "link")
    val link: String,

    @Json(name = "title")
    val title: String,

    @Json(name = "url")
    val url: String

)