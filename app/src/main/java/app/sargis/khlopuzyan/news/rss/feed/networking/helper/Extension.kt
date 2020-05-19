package app.sargis.khlopuzyan.news.rss.feed.networking.helper

import app.sargis.khlopuzyan.news.rss.feed.networking.callback.Result
import retrofit2.Response

fun <T : Any> Response<T>.getResult(): Result<T> {
    return if (this.isSuccessful) {
        val body = this.body()
        if (body != null) {
            Result.Success(body)
        } else {
            Result.Error(this.code(), this.errorBody())
        }
    } else {
        Result.Error(this.code(), this.errorBody())
    }
}