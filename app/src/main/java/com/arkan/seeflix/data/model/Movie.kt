package com.arkan.seeflix.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class Movie(
    var id: String = UUID.randomUUID().toString(),
    var imgUrl: String,
) : Parcelable
