package com.arkan.seeflix.data.datasource.bannerimghome

import com.arkan.seeflix.data.source.network.model.MoviesResponse
import com.arkan.seeflix.data.source.network.services.SeeflixApiServices

class BannerImgHomeApiDataSource(private val service: SeeflixApiServices) : BannerImgHomeDataSource {
    override suspend fun getBannerImgHome(): MoviesResponse {
        return service.getBannerImgHome()
    }
}
