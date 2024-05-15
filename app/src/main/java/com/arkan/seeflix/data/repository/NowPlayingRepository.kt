package com.arkan.seeflix.data.repository

import com.arkan.seeflix.data.datasource.nowplaying.NowPlayingDataSource
import com.arkan.seeflix.data.mapper.toNowPlaying
import com.arkan.seeflix.data.model.NowPlaying
import com.arkan.seeflix.utils.ResultWrapper
import com.arkan.seeflix.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface NowPlayingRepository {
    fun getNowPlaying(): Flow<ResultWrapper<List<NowPlaying>>>
}

class NowPlayingRepositoryImpl(
    private val dataSource: NowPlayingDataSource,
) : NowPlayingRepository {
    override fun getNowPlaying(): Flow<ResultWrapper<List<NowPlaying>>> {
        return proceedFlow {
            dataSource.getNowPlaying().results.toNowPlaying()
        }
    }
}
