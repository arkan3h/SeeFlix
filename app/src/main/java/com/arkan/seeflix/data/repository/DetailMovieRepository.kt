package com.arkan.seeflix.data.repository

import com.arkan.seeflix.data.datasource.detail.DetailMovieDataSource
import com.arkan.seeflix.data.mapper.toDetail
import com.arkan.seeflix.data.model.Detail
import com.arkan.seeflix.utils.ResultWrapper
import com.arkan.seeflix.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface DetailMovieRepository {
    fun getDetail(id: String): Flow<ResultWrapper<Detail>>
}

class DetailMovieRepositoryImpl(
    private val dataSource: DetailMovieDataSource,
) : DetailMovieRepository {
    override fun getDetail(id: String): Flow<ResultWrapper<Detail>> {
        return proceedFlow {
            dataSource.getDetail(id).toDetail()
        }
    }
}
