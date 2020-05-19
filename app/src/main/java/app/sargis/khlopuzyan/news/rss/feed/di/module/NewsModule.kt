package app.sargis.khlopuzyan.news.rss.feed.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.sargis.khlopuzyan.news.rss.feed.di.annotation.ViewModelKey
import app.sargis.khlopuzyan.news.rss.feed.repository.NewsRepository
import app.sargis.khlopuzyan.news.rss.feed.ui.newsfeed.NewsFeedFragment
import app.sargis.khlopuzyan.news.rss.feed.ui.newsfeed.NewsFeedViewModel
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module(includes = [NewsModule.ProvideViewModel::class])
interface NewsModule {

    @ContributesAndroidInjector(modules = [InjectViewModel::class])
    fun bind(): NewsFeedFragment

    @Module
    class ProvideViewModel {
        @Provides
        @IntoMap
        @ViewModelKey(NewsFeedViewModel::class)
        fun provideNewsViewModel(
            newsRepository: NewsRepository
        ): ViewModel = NewsFeedViewModel(newsRepository)
    }

    @Module
    class InjectViewModel {
        @Provides
        fun provideNewsViewModel(
            factory: ViewModelProvider.Factory,
            target: NewsFeedFragment
        ) = ViewModelProvider(target, factory)[NewsFeedViewModel::class.java]
    }

}