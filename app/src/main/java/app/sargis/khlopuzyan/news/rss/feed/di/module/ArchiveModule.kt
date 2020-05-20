package app.sargis.khlopuzyan.news.rss.feed.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.sargis.khlopuzyan.news.rss.feed.di.annotation.ViewModelKey
import app.sargis.khlopuzyan.news.rss.feed.repository.ArchiveRepository
import app.sargis.khlopuzyan.news.rss.feed.ui.archive.ArchiveFragment
import app.sargis.khlopuzyan.news.rss.feed.ui.archive.ArchiveViewModel
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module(includes = [ArchiveModule.ProvideViewModel::class])
interface ArchiveModule {

    @ContributesAndroidInjector(modules = [InjectViewModel::class])
    fun bind(): ArchiveFragment

    @Module
    class ProvideViewModel {
        @Provides
        @IntoMap
        @ViewModelKey(ArchiveViewModel::class)
        fun provideArchiveViewModel(
            archiveRepository: ArchiveRepository
        ): ViewModel = ArchiveViewModel(archiveRepository)
    }

    @Module
    class InjectViewModel {
        @Provides
        fun provideArchiveViewModel(
            factory: ViewModelProvider.Factory,
            target: ArchiveFragment
        ) = ViewModelProvider(target, factory)[ArchiveViewModel::class.java]
    }

}