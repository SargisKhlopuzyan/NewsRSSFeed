package app.sargis.khlopuzyan.news.rss.feed.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.sargis.khlopuzyan.news.rss.feed.di.annotation.ViewModelKey
import app.sargis.khlopuzyan.news.rss.feed.repository.NewsFeedRepository
import app.sargis.khlopuzyan.news.rss.feed.ui.newsFeed.NewsFeedFragment
import app.sargis.khlopuzyan.news.rss.feed.ui.newsFeed.NewsFeedViewModel
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module(includes = [NewsFeedModule.ProvideViewModel::class])
interface NewsFeedModule {

    @ContributesAndroidInjector(modules = [InjectViewModel::class])
    fun bind(): NewsFeedFragment

    @Module
    class ProvideViewModel {
        @Provides
        @IntoMap
        @ViewModelKey(NewsFeedViewModel::class)
        fun provideNewsFeedViewModel(
            newsFeedRepository: NewsFeedRepository
        ): ViewModel = NewsFeedViewModel(newsFeedRepository)
    }

    @Module
    class InjectViewModel {
        @Provides
        fun provideNewsFeedViewModel(
            factory: ViewModelProvider.Factory,
            target: NewsFeedFragment
        ) = ViewModelProvider(target, factory)[NewsFeedViewModel::class.java]
    }

}