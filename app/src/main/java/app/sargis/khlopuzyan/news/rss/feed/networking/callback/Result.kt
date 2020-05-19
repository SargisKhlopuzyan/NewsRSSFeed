package app.sargis.khlopuzyan.news.rss.feed.networking.callback

import okhttp3.ResponseBody

sealed class Result<out T> {
    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error(val errorCode: Int, val responseBody: ResponseBody?) : Result<Nothing>()
    data class Failure(val throwable: Throwable?) : Result<Nothing>()
}