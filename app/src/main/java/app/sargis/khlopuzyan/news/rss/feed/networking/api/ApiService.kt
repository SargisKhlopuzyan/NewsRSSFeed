package app.sargis.khlopuzyan.news.rss.feed.networking.api

import app.sargis.khlopuzyan.news.rss.feed.model.NewsFeed
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("api.json?")
    suspend fun searchNewsFeed(
        @Query(value = "rss_url") rss_url: String = "https://news.am/eng/rss/"
    ): Response<NewsFeed>

}