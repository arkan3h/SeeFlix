package com.arkan.seeflix.data.repository

import com.arkan.seeflix.data.datasource.popular.PopularDataSource
import com.arkan.seeflix.data.mapper.toPopular
import com.arkan.seeflix.data.model.Popular
import com.arkan.seeflix.utils.ResultWrapper
import com.arkan.seeflix.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface PopularRepository {
    fun getPopular(): Flow<ResultWrapper<List<Popular>>>
}

class PopularRepositoryImpl(
    private val dataSource: PopularDataSource,
) : PopularRepository {
    override fun getPopular(): Flow<ResultWrapper<List<Popular>>> {
        return proceedFlow {
            dataSource.getPopular().results.toPopular()
        }
    }
}
