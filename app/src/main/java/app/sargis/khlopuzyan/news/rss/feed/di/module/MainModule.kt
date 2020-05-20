package app.sargis.khlopuzyan.news.rss.feed.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.sargis.khlopuzyan.news.rss.feed.di.annotation.ViewModelKey
import app.sargis.khlopuzyan.news.rss.feed.ui.main.MainFragment
import app.sargis.khlopuzyan.news.rss.feed.ui.main.MainViewModel
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module(includes = [MainModule.ProvideViewModel::class])
interface MainModule {

    @ContributesAndroidInjector(modules = [InjectViewModel::class])
    fun bind(): MainFragment

    @Module
    class ProvideViewModel {
        @Provides
        @IntoMap
        @ViewModelKey(MainViewModel::class)
        fun provideMainViewModel(
        ): ViewModel = MainViewModel()
    }

    @Module
    class InjectViewModel {
        @Provides
        fun provideMainViewModel(
            factory: ViewModelProvider.Factory,
            target: MainFragment
        ) = ViewModelProvider(target, factory)[MainViewModel::class.java]
    }

}