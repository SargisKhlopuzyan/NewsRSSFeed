package app.sargis.khlopuzyan.news.rss.feed.ui.common

import app.sargis.khlopuzyan.news.rss.feed.util.DataLoadingState

interface BindableAdapter<T> {
    fun setItems(items: T?)
    fun setDataLoadingState(dataLoadingState: DataLoadingState?)
}