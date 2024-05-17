package com.arkan.seeflix.data.mapper

import com.arkan.seeflix.data.model.Detail
import com.arkan.seeflix.data.source.network.model.MovieDetailResponse

fun MovieDetailResponse?.toDetail() =
    Detail(
        backdropPath = this?.backdropPath.orEmpty(),
        id = this?.id.orEmpty(),
        imdbId = this?.imdbId.orEmpty(),
        overview = this?.overview.orEmpty(),
        posterPath = this?.posterPath.orEmpty(),
        releaseDate = this?.releaseDate.orEmpty(),
        title = this?.title.orEmpty(),
        voteAverage = this?.voteAverage ?: 0.0,
    )
