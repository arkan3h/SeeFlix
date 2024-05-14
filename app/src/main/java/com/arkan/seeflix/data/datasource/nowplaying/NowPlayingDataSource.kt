package com.arkan.seeflix.data.datasource.nowplaying

import com.arkan.seeflix.data.source.network.model.MoviesResponse

interface NowPlayingDataSource {
    suspend fun getNowPlaying(): MoviesResponse
}
