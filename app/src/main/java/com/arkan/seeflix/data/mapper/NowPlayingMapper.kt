package com.arkan.seeflix.data.mapper

import com.arkan.seeflix.data.model.NowPlaying
import com.arkan.seeflix.data.source.network.model.MovieItemResponse

fun MovieItemResponse?.toNowPlaying() =
    NowPlaying(
        imgUrl = this?.posterPath.orEmpty(),
    )

fun Collection<MovieItemResponse>?.toNowPlaying() =
    this?.map {
        it.toNowPlaying()
    } ?: listOf()
