package com.arkan.seeflix.data.datasource.listmovie

import com.arkan.seeflix.data.source.network.model.MoviesResponse
import com.arkan.seeflix.data.source.network.services.SeeflixApiServices

class ListMovieApiDataSource(private val service: SeeflixApiServices) : ListMovieDataSource {
    override suspend fun getListMovies(category: Int): MoviesResponse {
        return when (category) {
            1 -> service.getPopular()
            2 -> service.getTopRated()
            3 -> service.getUpcoming()
            else -> service.getNowPlaying()
        }
    }
}
