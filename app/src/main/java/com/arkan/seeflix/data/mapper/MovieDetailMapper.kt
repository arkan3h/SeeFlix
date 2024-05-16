package com.arkan.seeflix.data.mapper

import com.arkan.seeflix.data.model.MovieDetail
import com.arkan.seeflix.data.source.network.model.MovieDetailResponse

fun MovieDetailResponse.toMovieDetail(): MovieDetail {
    return MovieDetail(
        id = this.id,
        title = this.title,
        overview = this.overview,
        posterPath = this.posterPath,
        backdropPath = this.backdropPath,
        releaseDate = this.releaseDate,
        voteAverage = this.voteAverage
    )
}
