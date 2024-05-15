package com.arkan.seeflix.data.model

import java.util.UUID

data class NowPlaying(
    var id: String = UUID.randomUUID().toString(),
    var imgUrl: String,
)
