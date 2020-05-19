package app.sargis.khlopuzyan.news.rss.feed.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.sargis.khlopuzyan.news.rss.feed.di.annotation.ViewModelKey
import app.sargis.khlopuzyan.news.rss.feed.repository.NewsDetailsRepository
import app.sargis.khlopuzyan.news.rss.feed.ui.newsDetails.NewsDetailsFragment
import app.sargis.khlopuzyan.news.rss.feed.ui.newsDetails.NewsDetailsViewModel
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module(includes = [NewsDetailsModule.ProvideViewModel::class])
interface NewsDetailsModule {

    @ContributesAndroidInjector(modules = [InjectViewModel::class])
    fun bind(): NewsDetailsFragment

    @Module
    class ProvideViewModel {
        @Provides
        @IntoMap
        @ViewModelKey(NewsDetailsViewModel::class)
        fun provideNewsDetailsViewModel(
            newsDetailsRepository: NewsDetailsRepository
        ): ViewModel = NewsDetailsViewModel(newsDetailsRepository)
    }

    @Module
    class InjectViewModel {
        @Provides
        fun provideNewsDetailsViewModel(
            factory: ViewModelProvider.Factory,
            target: NewsDetailsFragment
        ) = ViewModelProvider(target, factory)[NewsDetailsViewModel::class.java]
    }

}