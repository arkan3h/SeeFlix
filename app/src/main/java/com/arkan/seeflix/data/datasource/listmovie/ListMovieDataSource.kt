package com.arkan.seeflix.data.datasource.listmovie

import com.arkan.seeflix.data.source.network.model.MoviesResponse

interface ListMovieDataSource {
    suspend fun getListMovies(category: Int): MoviesResponse
}
