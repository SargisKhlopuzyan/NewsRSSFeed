package app.sargis.khlopuzyan.news.rss.feed.di.component

import android.content.Context
import app.sargis.khlopuzyan.news.rss.feed.App
import app.sargis.khlopuzyan.news.rss.feed.di.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        ArchiveModule::class,
        NewsDetailsModule::class,
        NewsModule::class
    ]
)
interface AppComponent : AndroidInjector<App> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Context): AppComponent
    }

}