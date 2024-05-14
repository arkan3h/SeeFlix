package com.arkan.seeflix.data.repository

import com.arkan.seeflix.data.datasource.upcoming.UpcomingDataSource
import com.arkan.seeflix.data.mapper.toUpcoming
import com.arkan.seeflix.data.model.Upcoming
import com.arkan.seeflix.utils.ResultWrapper
import com.arkan.seeflix.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface UpcomingRepository {
    fun getUpcoming(): Flow<ResultWrapper<List<Upcoming>>>
}

class UpcomingRepositoryImpl(
    private val dataSource: UpcomingDataSource,
) : UpcomingRepository {
    override fun getUpcoming(): Flow<ResultWrapper<List<Upcoming>>> {
        return proceedFlow {
            dataSource.getUpcoming().results.toUpcoming()
        }
    }
}
