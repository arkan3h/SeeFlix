package com.arkan.seeflix.data.mapper

import com.arkan.seeflix.data.model.BannerImgHome
import com.arkan.seeflix.data.source.network.model.MovieItemResponse

fun MovieItemResponse?.toBannerImgHome() =
    BannerImgHome(
        imgUrl = this?.posterPath.orEmpty(),
        title = this?.title.orEmpty(),
        desc = this?.overview.orEmpty(),
    )

fun Collection<MovieItemResponse>?.toBannerImgHome() =
    this?.map {
        it.toBannerImgHome()
    } ?: listOf()
