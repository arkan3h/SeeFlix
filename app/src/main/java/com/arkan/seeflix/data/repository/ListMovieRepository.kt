package com.arkan.seeflix.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.arkan.seeflix.data.model.Movie
import com.arkan.seeflix.data.paging.ListMoviePagingSource
import com.arkan.seeflix.data.source.network.services.SeeflixApiServices
import kotlinx.coroutines.flow.Flow

interface ListMovieRepository {
    fun getListTopRated(): Flow<PagingData<Movie>>

    fun getListUpcoming(): Flow<PagingData<Movie>>

    fun getListPopular(): Flow<PagingData<Movie>>

    fun getListNowPlaying(): Flow<PagingData<Movie>>
}

class ListMovieRepositoryImpl(private val service: SeeflixApiServices) : ListMovieRepository {
    override fun getListTopRated(): Flow<PagingData<Movie>> =
        Pager(
            pagingSourceFactory = { ListMoviePagingSource(service) },
            config =
                PagingConfig(
                    pageSize = 20,
                ),
        ).flow

    override fun getListUpcoming(): Flow<PagingData<Movie>> =
        Pager(
            pagingSourceFactory = { ListMoviePagingSource(service) },
            config =
                PagingConfig(
                    pageSize = 20,
                ),
        ).flow

    override fun getListPopular(): Flow<PagingData<Movie>> =
        Pager(
            pagingSourceFactory = { ListMoviePagingSource(service) },
            config =
                PagingConfig(
                    pageSize = 20,
                ),
        ).flow

    override fun getListNowPlaying(): Flow<PagingData<Movie>> =
        Pager(
            pagingSourceFactory = { ListMoviePagingSource(service) },
            config =
                PagingConfig(
                    pageSize = 20,
                ),
        ).flow
}
