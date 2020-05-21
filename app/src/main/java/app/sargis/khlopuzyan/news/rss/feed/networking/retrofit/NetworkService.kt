package app.sargis.khlopuzyan.news.rss.feed.networking.retrofit

import app.sargis.khlopuzyan.news.rss.feed.BuildConfig
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class NetworkService {

    companion object {

        //        private const val BASE_URL = "https://api.rss2json.com"
//        private const val BASE_URL = "https://api.rss2json.com/v1/api.json"
//        private const val BASE_URL = "https://rss2json.com/#rss_url=https%3A%2F%2Fnews.am%2Feng%2Frss%2F"
//        private const val BASE_URL = "https://api.rss2json.com/v1/api.json?rss_url=https%3A%2F%2Fnews.am%2Feng%2Frss%2F"
        private const val BASE_URL = "https://api.rss2json.com/v1/"

        fun initOkHttpClient(): OkHttpClient {
            return OkHttpClient.Builder().apply {
                readTimeout(60, TimeUnit.SECONDS)
                connectTimeout(60, TimeUnit.SECONDS)

                // interceptor for logging
                if (BuildConfig.DEBUG) {
                    val logging = HttpLoggingInterceptor()
                    logging.level = HttpLoggingInterceptor.Level.BASIC
                    addInterceptor(logging)
                }
            }.build()
        }

        fun initRetrofit(okHttpClient: OkHttpClient): Retrofit =
            Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
//                .addConverterFactory(
//                    MoshiConverterFactory.create(
//                        Moshi.Builder()
//                            .build()
//                    )
//                )
                .build()
    }

}