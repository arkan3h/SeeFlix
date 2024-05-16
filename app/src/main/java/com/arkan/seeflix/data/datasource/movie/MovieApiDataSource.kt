package com.arkan.seeflix.data.datasource.movie

import com.arkan.seeflix.data.source.network.model.MoviesResponse
import com.arkan.seeflix.data.source.network.services.SeeflixApiServices

class MovieApiDataSource(private val service: SeeflixApiServices) : MovieDataSource {
    override suspend fun getNowPlaying(): MoviesResponse {
        return service.getNowPlaying()
    }

    override suspend fun getPopular(): MoviesResponse {
        return service.getPopular()
    }

    override suspend fun getTopRated(): MoviesResponse {
        return service.getTopRated()
    }

    override suspend fun getUpcoming(): MoviesResponse {
        return service.getUpcoming()
    }
}
