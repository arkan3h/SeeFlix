package com.arkan.seeflix.di

import com.arkan.seeflix.data.datasource.BookmarkDataSource
import com.arkan.seeflix.data.datasource.BookmarkDataSourceImpl
import com.arkan.seeflix.data.datasource.bannerimghome.BannerImgHomeApiDataSource
import com.arkan.seeflix.data.datasource.bannerimghome.BannerImgHomeDataSource
import com.arkan.seeflix.data.datasource.nowplaying.NowPlayingApiDataSource
import com.arkan.seeflix.data.datasource.nowplaying.NowPlayingDataSource
import com.arkan.seeflix.data.datasource.popular.PopularApiDataSource
import com.arkan.seeflix.data.datasource.popular.PopularDataSource
import com.arkan.seeflix.data.datasource.toprated.TopRatedApiDataSource
import com.arkan.seeflix.data.datasource.toprated.TopRatedDataSource
import com.arkan.seeflix.data.datasource.upcoming.UpcomingApiDataSource
import com.arkan.seeflix.data.datasource.upcoming.UpcomingDataSource
import com.arkan.seeflix.data.repository.BannerImgHomeRepository
import com.arkan.seeflix.data.repository.BannerImgHomeRepositoryImpl
import com.arkan.seeflix.data.repository.BookmarkRepository
import com.arkan.seeflix.data.repository.BookmarkRepositoryImpl
import com.arkan.seeflix.data.repository.NowPlayingRepository
import com.arkan.seeflix.data.repository.NowPlayingRepositoryImpl
import com.arkan.seeflix.data.repository.PopularRepository
import com.arkan.seeflix.data.repository.PopularRepositoryImpl
import com.arkan.seeflix.data.repository.TopRatedRepository
import com.arkan.seeflix.data.repository.TopRatedRepositoryImpl
import com.arkan.seeflix.data.repository.UpcomingRepository
import com.arkan.seeflix.data.repository.UpcomingRepositoryImpl
import com.arkan.seeflix.data.source.local.database.AppDatabase
import com.arkan.seeflix.data.source.local.database.dao.BookmarkDao
import com.arkan.seeflix.presentation.bookmark.BookmarkViewModel
import com.arkan.seeflix.presentation.home.HomeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

object AppModules {
    private val localModule =
        module {
            single<AppDatabase> { AppDatabase.getInstance(androidContext()) }
            single<BookmarkDao> { get<AppDatabase>().bookmarkDao() }
        }

    private val datasource =
        module {
            single<BookmarkDataSource> { BookmarkDataSourceImpl(get()) }
            single<BannerImgHomeDataSource> { BannerImgHomeApiDataSource(get()) }
            single<NowPlayingDataSource> { NowPlayingApiDataSource(get()) }
            single<PopularDataSource> { PopularApiDataSource(get()) }
            single<TopRatedDataSource> { TopRatedApiDataSource(get()) }
            single<UpcomingDataSource> { UpcomingApiDataSource(get()) }
        }

    private val repository =
        module {
            single<BookmarkRepository> { BookmarkRepositoryImpl(get()) }
            single<BannerImgHomeRepository> { BannerImgHomeRepositoryImpl(get()) }
            single<NowPlayingRepository> { NowPlayingRepositoryImpl(get()) }
            single<PopularRepository> { PopularRepositoryImpl(get()) }
            single<UpcomingRepository> { UpcomingRepositoryImpl(get()) }
            single<TopRatedRepository> { TopRatedRepositoryImpl(get()) }
        }

    private val viewModelModule =
        module {
            viewModelOf(::BookmarkViewModel)
            viewModelOf(::HomeViewModel)
        }

    val modules =
        listOf(
            localModule,
            datasource,
            repository,
            viewModelModule,
        )
}
