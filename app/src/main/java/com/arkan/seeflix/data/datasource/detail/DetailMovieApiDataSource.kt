package com.arkan.seeflix.data.datasource.detail

import com.arkan.seeflix.data.source.network.model.MovieDetailResponse
import com.arkan.seeflix.data.source.network.services.SeeflixApiServices

class DetailMovieApiDataSource(private val services: SeeflixApiServices) : DetailMovieDataSource {
    override suspend fun getDetail(id: String): MovieDetailResponse {
        return services.getMovieDetail(id = id)
    }
}
