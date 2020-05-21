package app.sargis.khlopuzyan.news.rss.feed.util

sealed class DataLoadingState {
    object Loading : DataLoadingState()
    object Loaded : DataLoadingState()
    class Failure(val throwable: Throwable?) : DataLoadingState()
}