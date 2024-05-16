package com.arkan.seeflix.data.datasource.bookmark

import app.cash.turbine.test
import com.arkan.seeflix.data.source.local.database.dao.BookmarkDao
import com.arkan.seeflix.data.source.local.database.entity.BookmarkEntity
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class BookmarkDataSourceImplTest {
    @MockK
    lateinit var dao: BookmarkDao

    private lateinit var ds: BookmarkDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        ds = BookmarkDataSourceImpl(dao)
    }

    private val entity1 = mockk<BookmarkEntity>()
    private val entity2 = mockk<BookmarkEntity>()
    private val listEntity = listOf(entity1, entity2)

    @Test
    fun getAllBookmark() {
        val mockFlow =
            flow {
                emit(listEntity)
            }

        every {
            dao.getAllBookmark()
        } returns mockFlow

        runTest {
            ds.getAllBookmark().test {
                val result = awaitItem()
                assertEquals(listEntity.size, result.size)
                assertEquals(entity1, result[0])
                assertEquals(entity2, result[1])
                awaitComplete()
            }
        }
    }

    @Test
    fun checkBookmarkById() {
        val mockFlow =
            flow {
                emit(listEntity)
            }

        every {
            dao.checkBookmarkById(any())
        } returns mockFlow

        runTest {
            ds.checkBookmarkById("11111").test {
                val result = awaitItem()
                assertEquals(listEntity.size, result.size)
                assertEquals(entity1, result[0])
                assertEquals(entity2, result[1])
                awaitComplete()
            }
        }
    }

    @Test
    fun addBookmark() {
        runTest {
            val mockEntity = mockk<BookmarkEntity>()
            coEvery {
                dao.addBookmark(any())
            } returns 1

            val result = ds.addBookmark(mockEntity)
            coVerify {
                dao.addBookmark(any())
                assertEquals(1, result)
            }
        }
    }

    @Test
    fun deleteBookmark() {
        runTest {
            val mockEntity = mockk<BookmarkEntity>()
            coEvery {
                dao.deleteBookmark(any())
            } returns 1

            val result = ds.deleteBookmark(mockEntity)
            coVerify {
                dao.deleteBookmark(any())
                assertEquals(1, result)
            }
        }
    }

    @Test
    fun removeBookmark() {
        runTest {
            coEvery {
                dao.removeBookmark(any())
            } returns 1

            val result = ds.removeBookmark("11111")
            coVerify {
                dao.removeBookmark(any())
                assertEquals(1, result)
            }
        }
    }

    @Test
    fun deleteAll() {
        runTest {
            coEvery {
                dao.deleteAll()
            } returns Unit

            val result = ds.deleteAll()
            coVerify {
                dao.deleteAll()
                assertEquals(Unit, result)
            }
        }
    }
}
