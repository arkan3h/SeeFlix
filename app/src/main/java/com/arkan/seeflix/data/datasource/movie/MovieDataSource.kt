package com.arkan.seeflix.data.datasource.movie

import com.arkan.seeflix.data.source.network.model.MovieDetailResponse
import com.arkan.seeflix.data.source.network.services.SeeflixApiServices

interface MovieDataSource {
    suspend fun getDetail(id: String): MovieDetailResponse
}

class MovieDataSourceImpl(
    private val service: SeeflixApiServices
) : MovieDataSource {
    override suspend fun getDetail(id: String): MovieDetailResponse {
        return service.getMovieDetail(id)
    }
}
