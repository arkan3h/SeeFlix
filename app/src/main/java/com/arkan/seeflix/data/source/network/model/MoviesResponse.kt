package com.arkan.seeflix.data.source.network.model

import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    @SerializedName("dates") var dates: Dates?,
    @SerializedName("page") var page: Int?,
    @SerializedName("results") var results: List<MovieItemResponse>?,
    @SerializedName("total_pages") var totalPages: Int?,
    @SerializedName("total_results") var totalResults: Int?,
)
