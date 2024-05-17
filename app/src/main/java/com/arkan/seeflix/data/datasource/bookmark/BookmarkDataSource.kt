package com.arkan.seeflix.data.datasource.bookmark

import com.arkan.seeflix.data.source.local.database.dao.BookmarkDao
import com.arkan.seeflix.data.source.local.database.entity.BookmarkEntity
import kotlinx.coroutines.flow.Flow

interface BookmarkDataSource {
    fun getAllBookmark(): Flow<List<BookmarkEntity>>

    fun checkBookmarkById(movieId: String): Flow<List<BookmarkEntity>>

    suspend fun addBookmark(bookmark: BookmarkEntity): Long

    suspend fun deleteBookmark(bookmark: BookmarkEntity): Int

    suspend fun removeBookmark(movieId: String): Int

    suspend fun deleteAll()
}

class BookmarkDataSourceImpl(private val dao: BookmarkDao) : BookmarkDataSource {
    override fun getAllBookmark(): Flow<List<BookmarkEntity>> = dao.getAllBookmark()

    override fun checkBookmarkById(movieId: String): Flow<List<BookmarkEntity>> = dao.checkBookmarkById(movieId)

    override suspend fun addBookmark(bookmark: BookmarkEntity): Long = dao.addBookmark(bookmark)

    override suspend fun deleteBookmark(bookmark: BookmarkEntity): Int = dao.deleteBookmark(bookmark)

    override suspend fun removeBookmark(movieId: String): Int = dao.removeBookmark(movieId)

    override suspend fun deleteAll() = dao.deleteAll()
}
