package com.arkan.seeflix.data.datasource.popular

import com.arkan.seeflix.data.source.network.model.MoviesResponse

interface PopularDataSource {
    suspend fun getPopular(): MoviesResponse
}
