package com.arkan.seeflix.data.mapper

import com.arkan.seeflix.data.model.TopRated
import com.arkan.seeflix.data.source.network.model.MovieItemResponse

fun MovieItemResponse?.toTopRated() =
    TopRated(
        imgUrl = this?.posterPath.orEmpty(),
    )

fun Collection<MovieItemResponse>?.toTopRated() =
    this?.map {
        it.toTopRated()
    } ?: listOf()
