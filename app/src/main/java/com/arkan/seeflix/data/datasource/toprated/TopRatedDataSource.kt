package com.arkan.seeflix.data.datasource.toprated

import com.arkan.seeflix.data.source.network.model.MoviesResponse

interface TopRatedDataSource {
    suspend fun getTopRated(): MoviesResponse
}
