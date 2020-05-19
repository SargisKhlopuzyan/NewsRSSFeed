package app.sargis.khlopuzyan.news.rss.feed.di.module

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.sargis.khlopuzyan.news.rss.feed.database.DatabaseManager
import app.sargis.khlopuzyan.news.rss.feed.di.factory.AppViewModelFactory
import app.sargis.khlopuzyan.news.rss.feed.networking.api.ApiService
import app.sargis.khlopuzyan.news.rss.feed.networking.retrofit.NetworkService
import app.sargis.khlopuzyan.news.rss.feed.repository.*
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.create
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Provider
import javax.inject.Singleton

@Module(includes = [AppModule.ProvideViewModel::class])
abstract class AppModule {

    @Module
    class ProvideViewModel {

        @Provides
        @Singleton
        fun provideExecutor(): Executor = Executors.newFixedThreadPool(2)

        @Provides
        @Singleton
        fun provideOkHttpClient(/*addApiKeyInterceptor: AddApiKeyInterceptor*/): OkHttpClient =
            NetworkService.initOkHttpClient(/*addApiKeyInterceptor*/)

        @Provides
        @Singleton
        fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
            NetworkService.initRetrofit(okHttpClient)

        @Provides
        @Singleton
        fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create()

        @Provides
        @Singleton
        fun provideDatabaseManager(
            context: Context
        ): DatabaseManager = DatabaseManager(context)

        @Provides
        fun provideViewModelFactory(
            providers: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
        ): ViewModelProvider.Factory = AppViewModelFactory(providers)

        @Provides
        @Singleton
        fun provideArchiveRepository(
            databaseManager: DatabaseManager
        ): ArchiveRepository =
            ArchiveRepositoryImpl(
                databaseManager
            )

        @Provides
        @Singleton
        fun provideTopAlbumsRepository(
            apiService: ApiService,
            databaseManager: DatabaseManager
        ): NewsRepository =
            NewsRepositoryImpl(
                apiService,
                databaseManager,
                CoroutineScope(Job() + Dispatchers.IO)
            )

        @Provides
        @Singleton
        fun provideNewsDetailsRepository(
            databaseManager: DatabaseManager
        ): NewsDetailsRepository =
            NewsDetailsRepositoryImpl(
                databaseManager
            )
    }

}