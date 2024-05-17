package com.arkan.seeflix.data.source.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmark")
data class BookmarkEntity(
    @PrimaryKey
    var movieId: String,
    @ColumnInfo(name = "movie_poster_path")
    var moviePosterPath: String?,
)
