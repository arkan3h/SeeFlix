package com.arkan.seeflix.data.mapper

import com.arkan.seeflix.data.model.Bookmark
import com.arkan.seeflix.data.source.local.database.entity.BookmarkEntity

fun Bookmark.toBookmarkEntity() =
    BookmarkEntity(
        movieId = this.movieId,
        moviePosterPath = this.moviePosterPath,
    )

fun BookmarkEntity.toBookmark() =
    Bookmark(
        movieId = this.movieId,
        moviePosterPath = this.moviePosterPath,
    )

fun List<BookmarkEntity>.toBookmarkList() = this.map { it.toBookmark() }
