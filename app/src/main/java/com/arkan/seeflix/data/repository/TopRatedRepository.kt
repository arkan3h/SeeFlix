package com.arkan.seeflix.data.repository

import com.arkan.seeflix.data.datasource.toprated.TopRatedDataSource
import com.arkan.seeflix.data.mapper.toTopRated
import com.arkan.seeflix.data.model.TopRated
import com.arkan.seeflix.utils.ResultWrapper
import com.arkan.seeflix.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface TopRatedRepository {
    fun getTopRated(): Flow<ResultWrapper<List<TopRated>>>
}

class TopRatedRepositoryImpl(
    private val dataSource: TopRatedDataSource,
) : TopRatedRepository {
    override fun getTopRated(): Flow<ResultWrapper<List<TopRated>>> {
        return proceedFlow {
            dataSource.getTopRated().results.toTopRated()
        }
    }
}
