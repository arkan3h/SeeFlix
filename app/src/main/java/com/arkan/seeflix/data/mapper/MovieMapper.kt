package com.arkan.seeflix.data.mapper

import com.arkan.seeflix.data.model.Movie
import com.arkan.seeflix.data.source.network.model.MovieItemResponse

fun MovieItemResponse?.toMovie() =
    Movie(
        id = this?.id.orEmpty(),
        imgUrl = this?.posterPath.orEmpty(),
    )

fun Collection<MovieItemResponse>?.toMovie() =
    this?.map {
        it.toMovie()
    } ?: listOf()
