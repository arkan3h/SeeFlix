package com.arkan.seeflix.data.datasource.movie

import com.arkan.seeflix.data.source.network.model.MoviesResponse

interface MovieDataSource {
    suspend fun getNowPlaying(): MoviesResponse

    suspend fun getPopular(): MoviesResponse

    suspend fun getTopRated(): MoviesResponse

    suspend fun getUpcoming(): MoviesResponse
}
