package com.arkan.seeflix.di

import com.arkan.seeflix.data.datasource.bannerimghome.BannerImgHomeApiDataSource
import com.arkan.seeflix.data.datasource.bannerimghome.BannerImgHomeDataSource
import com.arkan.seeflix.data.datasource.bookmark.BookmarkDataSource
import com.arkan.seeflix.data.datasource.bookmark.BookmarkDataSourceImpl
import com.arkan.seeflix.data.datasource.detail.DetailMovieApiDataSource
import com.arkan.seeflix.data.datasource.detail.DetailMovieDataSource
import com.arkan.seeflix.data.datasource.listmovie.ListMovieApiDataSource
import com.arkan.seeflix.data.datasource.listmovie.ListMovieDataSource
import com.arkan.seeflix.data.datasource.movie.MovieApiDataSource
import com.arkan.seeflix.data.datasource.movie.MovieDataSource
import com.arkan.seeflix.data.repository.BannerImgHomeRepository
import com.arkan.seeflix.data.repository.BannerImgHomeRepositoryImpl
import com.arkan.seeflix.data.repository.BookmarkRepository
import com.arkan.seeflix.data.repository.BookmarkRepositoryImpl
import com.arkan.seeflix.data.repository.DetailMovieRepository
import com.arkan.seeflix.data.repository.DetailMovieRepositoryImpl
import com.arkan.seeflix.data.repository.ListMovieRepository
import com.arkan.seeflix.data.repository.ListMovieRepositoryImpl
import com.arkan.seeflix.data.repository.MovieRepository
import com.arkan.seeflix.data.repository.MovieRepositoryImpl
import com.arkan.seeflix.data.source.local.database.AppDatabase
import com.arkan.seeflix.data.source.local.database.dao.BookmarkDao
import com.arkan.seeflix.data.source.network.services.SeeflixApiServices
import com.arkan.seeflix.presentation.bookmark.BookmarkViewModel
import com.arkan.seeflix.presentation.detail.DetailViewModel
import com.arkan.seeflix.presentation.home.HomeViewModel
import com.arkan.seeflix.presentation.listmovie.ListMovieViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

object AppModules {
    private val localModule =
        module {
            single<AppDatabase> { AppDatabase.getInstance(androidContext()) }
            single<BookmarkDao> { get<AppDatabase>().bookmarkDao() }
        }

    private val networkModule =
        module {
            single<SeeflixApiServices> { SeeflixApiServices.invoke() }
        }

    private val datasource =
        module {
            single<BookmarkDataSource> { BookmarkDataSourceImpl(get()) }
            single<BannerImgHomeDataSource> { BannerImgHomeApiDataSource(get()) }
            single<MovieDataSource> { MovieApiDataSource(get()) }
            single<ListMovieDataSource> { ListMovieApiDataSource(get()) }
            single<DetailMovieDataSource> { DetailMovieApiDataSource(get()) }
        }

    private val repository =
        module {
            single<BookmarkRepository> { BookmarkRepositoryImpl(get()) }
            single<BannerImgHomeRepository> { BannerImgHomeRepositoryImpl(get()) }
            single<MovieRepository> { MovieRepositoryImpl(get()) }
            single<ListMovieRepository> { ListMovieRepositoryImpl(get()) }
            single<DetailMovieRepository> { DetailMovieRepositoryImpl(get()) }
        }

    private val viewModelModule =
        module {
            viewModelOf(::BookmarkViewModel)
            viewModelOf(::HomeViewModel)
            viewModelOf(::ListMovieViewModel)
            viewModelOf(::DetailViewModel)
        }

    val modules =
        listOf(
            localModule,
            networkModule,
            datasource,
            repository,
            viewModelModule,
        )
}
