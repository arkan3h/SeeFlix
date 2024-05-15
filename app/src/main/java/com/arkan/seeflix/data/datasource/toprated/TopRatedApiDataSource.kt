package com.arkan.seeflix.data.datasource.toprated

import com.arkan.seeflix.data.source.network.model.MoviesResponse
import com.arkan.seeflix.data.source.network.services.SeeflixApiServices

class TopRatedApiDataSource(private val service: SeeflixApiServices) : TopRatedDataSource {
    override suspend fun getTopRated(): MoviesResponse {
        return service.getTopRated()
    }
}
