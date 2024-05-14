package com.arkan.seeflix.data.source.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.arkan.seeflix.data.source.local.database.entity.BookmarkEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarkDao {
    @Query("SELECT * FROM BOOKMARK")
    fun getAllBookmark(): Flow<List<BookmarkEntity>>

    @Query("SELECT * FROM BOOKMARK WHERE movieId = :movieId ")
    fun checkBookmarkById(movieId: String): Flow<List<BookmarkEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addBookmark(bookmark: BookmarkEntity): Long

    @Delete
    suspend fun deleteBookmark(bookmark: BookmarkEntity): Int

    @Query("DELETE FROM BOOKMARK WHERE movieId = :movieId")
    suspend fun removeBookmark(movieId: String): Int

    @Query("DELETE FROM BOOKMARK")
    suspend fun deleteAll()
}
