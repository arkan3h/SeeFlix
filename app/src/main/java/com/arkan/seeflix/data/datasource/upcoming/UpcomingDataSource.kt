package com.arkan.seeflix.data.datasource.upcoming

import com.arkan.seeflix.data.source.network.model.MoviesResponse

interface UpcomingDataSource {
    suspend fun getUpcoming(): MoviesResponse
}
