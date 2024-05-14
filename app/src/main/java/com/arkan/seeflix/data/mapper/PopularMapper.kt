package com.arkan.seeflix.data.mapper

import com.arkan.seeflix.data.model.Popular
import com.arkan.seeflix.data.source.network.model.MovieItemResponse

fun MovieItemResponse?.toPopular() =
    Popular(
        imgUrl = this?.posterPath.orEmpty(),
    )

fun Collection<MovieItemResponse>?.toPopular() =
    this?.map {
        it.toPopular()
    } ?: listOf()
