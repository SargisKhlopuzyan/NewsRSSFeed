package app.sargis.khlopuzyan.news.rss.feed.networking.api

import app.sargis.khlopuzyan.news.rss.feed.model.News
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("?")
    suspend fun searchArtist(
//        @Query(value = "format") format: String = "json"
    ): Response<News>

}