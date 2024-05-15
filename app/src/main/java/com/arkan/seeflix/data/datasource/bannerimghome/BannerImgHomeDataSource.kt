package com.arkan.seeflix.data.datasource.bannerimghome

import com.arkan.seeflix.data.source.network.model.MoviesResponse

interface BannerImgHomeDataSource {
    suspend fun getBannerImgHome(): MoviesResponse
}
