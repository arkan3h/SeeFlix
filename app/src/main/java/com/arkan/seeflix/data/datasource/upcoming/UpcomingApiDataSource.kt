package com.arkan.seeflix.data.datasource.upcoming

import com.arkan.seeflix.data.source.network.model.MoviesResponse
import com.arkan.seeflix.data.source.network.services.SeeflixApiServices

class UpcomingApiDataSource(private val service: SeeflixApiServices) : UpcomingDataSource {
    override suspend fun getUpcoming(): MoviesResponse {
        return service.getUpcoming()
    }
}
