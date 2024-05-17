package com.arkan.seeflix.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    var id: String,
    var imgUrl: String,
) : Parcelable
