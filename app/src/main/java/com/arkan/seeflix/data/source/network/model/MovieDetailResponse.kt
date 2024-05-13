package com.arkan.seeflix.data.source.network.model

import com.google.gson.annotations.SerializedName

data class MovieDetailResponse(
    @SerializedName("backdrop_path") var backdropPath: String?,
//    @SerializedName("genres") var genres: List<Genres>,
    @SerializedName("id") var id: Int?,
    @SerializedName("imdb_id") var imdbId: String?,
    @SerializedName("overview") var overview: String?,
    @SerializedName("poster_path") var posterPath: String?,
    @SerializedName("release_date") var releaseDate: String?,
    @SerializedName("title") var title: String?,
    @SerializedName("vote_average") var voteAverage: Double?,
)
