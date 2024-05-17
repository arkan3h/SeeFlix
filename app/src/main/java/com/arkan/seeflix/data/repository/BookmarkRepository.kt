package com.arkan.seeflix.data.repository

import com.arkan.seeflix.data.datasource.bookmark.BookmarkDataSource
import com.arkan.seeflix.data.mapper.toBookmarkEntity
import com.arkan.seeflix.data.mapper.toBookmarkList
import com.arkan.seeflix.data.model.Bookmark
import com.arkan.seeflix.data.model.Detail
import com.arkan.seeflix.data.source.local.database.entity.BookmarkEntity
import com.arkan.seeflix.utils.ResultWrapper
import com.arkan.seeflix.utils.proceed
import com.arkan.seeflix.utils.proceedFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

interface BookmarkRepository {
    fun getAllBookmark(): Flow<ResultWrapper<List<Bookmark>>>

    fun checkBookmarkById(movieId: String): Flow<List<BookmarkEntity>>

    fun addBookmark(detail: Detail): Flow<ResultWrapper<Boolean>>

    fun deleteBookmark(bookmark: Bookmark): Flow<ResultWrapper<Boolean>>

    fun removeBookmark(movieId: String): Flow<ResultWrapper<Boolean>>

    fun deleteAll(): Flow<ResultWrapper<Boolean>>
}

class BookmarkRepositoryImpl(private val datasource: BookmarkDataSource) : BookmarkRepository {
    override fun getAllBookmark(): Flow<ResultWrapper<List<Bookmark>>> {
        return datasource.getAllBookmark()
            .map {
                proceed {
                    val result = it.toBookmarkList()
                    result
                }
            }.map {
                if (it.payload?.isEmpty() == false) return@map it
                ResultWrapper.Empty(it.payload)
            }.catch {
                emit(ResultWrapper.Error(Exception(it)))
            }.onStart {
                emit(ResultWrapper.Loading())
            }
    }

    override fun checkBookmarkById(movieId: String): Flow<List<BookmarkEntity>> {
        return datasource.checkBookmarkById(movieId)
    }

    override fun addBookmark(detail: Detail): Flow<ResultWrapper<Boolean>> {
        return proceedFlow {
            val affectedRow =
                datasource.addBookmark(
                    BookmarkEntity(
                        movieId = detail.id,
                        moviePosterPath = detail.posterPath,
                    ),
                )
            affectedRow > 0
        }
    }

    override fun deleteBookmark(bookmark: Bookmark): Flow<ResultWrapper<Boolean>> {
        return proceedFlow { datasource.deleteBookmark(bookmark.toBookmarkEntity()) > 0 }
    }

    override fun removeBookmark(movieId: String): Flow<ResultWrapper<Boolean>> {
        return proceedFlow { datasource.removeBookmark(movieId) > 0 }
    }

    override fun deleteAll(): Flow<ResultWrapper<Boolean>> {
        return proceedFlow {
            datasource.deleteAll()
            true
        }
    }
}
