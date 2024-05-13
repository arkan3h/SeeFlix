package com.arkan.seeflix.data.source.network.model

import com.google.gson.annotations.SerializedName

data class MovieItemResponse(
    @SerializedName("id") var id: Int?,
    @SerializedName("overview") var overview: String?,
    @SerializedName("poster_path") var posterPath: String?,
    @SerializedName("title") var title: String?,
)
