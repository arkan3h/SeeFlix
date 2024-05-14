package com.arkan.seeflix.data.datasource.popular

import com.arkan.seeflix.data.source.network.model.MoviesResponse
import com.arkan.seeflix.data.source.network.services.SeeflixApiServices

class PopularApiDataSource(private val service: SeeflixApiServices) : PopularDataSource {
    override suspend fun getPopular(): MoviesResponse {
        return service.getPopular()
    }
}
