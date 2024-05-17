package com.arkan.seeflix.data.datasource.detail

import com.arkan.seeflix.data.source.network.model.MovieDetailResponse

interface DetailMovieDataSource {
    suspend fun getDetail(id: String): MovieDetailResponse
}
