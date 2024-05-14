package com.arkan.seeflix.data.datasource.nowplaying

import com.arkan.seeflix.data.source.network.model.MoviesResponse
import com.arkan.seeflix.data.source.network.services.SeeflixApiServices

class NowPlayingApiDataSource(private val service: SeeflixApiServices) : NowPlayingDataSource {
    override suspend fun getNowPlaying(): MoviesResponse {
        return service.getNowPlaying()
    }
}
