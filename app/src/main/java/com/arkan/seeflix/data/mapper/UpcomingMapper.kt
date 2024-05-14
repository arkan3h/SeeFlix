package com.arkan.seeflix.data.mapper

import com.arkan.seeflix.data.model.Upcoming
import com.arkan.seeflix.data.source.network.model.MovieItemResponse

fun MovieItemResponse?.toUpcoming() =
    Upcoming(
        imgUrl = this?.posterPath.orEmpty(),
    )

fun Collection<MovieItemResponse>?.toUpcoming() =
    this?.map {
        it.toUpcoming()
    } ?: listOf()
