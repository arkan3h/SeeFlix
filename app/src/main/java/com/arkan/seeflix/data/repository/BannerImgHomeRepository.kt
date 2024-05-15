package com.arkan.seeflix.data.repository

import com.arkan.seeflix.data.datasource.bannerimghome.BannerImgHomeDataSource
import com.arkan.seeflix.data.mapper.toBannerImgHome
import com.arkan.seeflix.data.model.BannerImgHome
import com.arkan.seeflix.utils.ResultWrapper
import com.arkan.seeflix.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface BannerImgHomeRepository {
    fun getBannerImgHome(): Flow<ResultWrapper<List<BannerImgHome>>>
}

class BannerImgHomeRepositoryImpl(
    private val dataSource: BannerImgHomeDataSource,
) : BannerImgHomeRepository {
    override fun getBannerImgHome(): Flow<ResultWrapper<List<BannerImgHome>>> {
        return proceedFlow {
            dataSource.getBannerImgHome().results.toBannerImgHome()
        }
    }
}
