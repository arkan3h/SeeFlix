package com.arkan.seeflix.data.model

import java.util.UUID

data class BannerImgHome(
    var id: String = UUID.randomUUID().toString(),
    var imgUrl: String,
    var title: String,
    var desc: String,
)
