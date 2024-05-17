package com.arkan.seeflix.data.repository

import app.cash.turbine.test
import com.arkan.seeflix.data.datasource.bookmark.BookmarkDataSource
import com.arkan.seeflix.data.model.Bookmark
import com.arkan.seeflix.data.model.Detail
import com.arkan.seeflix.data.source.local.database.entity.BookmarkEntity
import com.arkan.seeflix.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class BookmarkRepositoryImplTest {
    @MockK
    lateinit var ds: BookmarkDataSource

    private lateinit var repo: BookmarkRepository

    private val entity1 =
        BookmarkEntity(
            movieId = "1",
            moviePosterPath = "aaa",
        )
    private val entity2 =
        BookmarkEntity(
            movieId = "2",
            moviePosterPath = "aaa2",
        )
    private val mockBookmarkList = listOf(entity1, entity2)
    private val mockBookmark =
        Bookmark(
            movieId = "1",
            moviePosterPath = "aaa",
        )
    private val mockDetail =
        Detail(
            backdropPath = "aaa",
            id = "111",
            imdbId = "aaa",
            overview = "aaa",
            posterPath = "aaa",
            releaseDate = "aaa",
            title = "aaa",
            voteAverage = 9.0,
        )

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repo = BookmarkRepositoryImpl(ds)
    }

    @Test
    fun getAllBookmarkLoading() {
        val mockFlow =
            flow {
                emit(mockBookmarkList)
            }
        every {
            ds.getAllBookmark()
        } returns mockFlow

        runTest {
            repo.getAllBookmark().map {
                delay(100)
                it
            }.test {
                delay(150)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Loading)
                assertEquals(null, actualData.payload?.size)
                verify { ds.getAllBookmark() }
            }
        }
    }

    @Test
    fun getAllBookmarkSuccess() {
        val mockFlow =
            flow {
                emit(mockBookmarkList)
            }
        every {
            ds.getAllBookmark()
        } returns mockFlow

        runTest {
            repo.getAllBookmark().map {
                delay(100)
                it
            }.test {
                delay(250)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                assertEquals(mockBookmarkList.size, actualData.payload?.size)
                verify { ds.getAllBookmark() }
            }
        }
    }

    @Test
    fun getAllBookmarkError() {
        every {
            ds.getAllBookmark()
        } returns
            flow {
                throw IllegalStateException("Mock Error")
            }

        runTest {
            repo.getAllBookmark().map {
                delay(100)
                it
            }.test {
                delay(250)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Error)
                verify { ds.getAllBookmark() }
            }
        }
    }

    @Test
    fun getAllBookmarkEmpty() {
        val mockBookmarkList = listOf<BookmarkEntity>()
        val mockFlow =
            flow {
                emit(mockBookmarkList)
            }
        every {
            ds.getAllBookmark()
        } returns mockFlow

        runTest {
            repo.getAllBookmark().map {
                delay(100)
                it
            }.test {
                delay(250)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Empty)
                assertEquals(0, actualData.payload?.size)
                verify { ds.getAllBookmark() }
            }
        }
    }

    @Test
    fun checkBookmarkById() {
        val mockFlow =
            flow {
                emit(mockBookmarkList)
            }

        every {
            ds.checkBookmarkById(any())
        } returns mockFlow

        runTest {
            repo.checkBookmarkById("11111").test {
                val result = awaitItem()
                assertEquals(mockBookmarkList.size, result.size)
                assertEquals(entity1, result[0])
                assertEquals(entity2, result[1])
                awaitComplete()
            }
        }
    }

    @Test
    fun addBookmarkLoading() {
        val mockProduct = mockk<Bookmark>(relaxed = true)
        every {
            mockProduct.movieId
        } returns "1"

        runTest {
            coEvery {
                ds.addBookmark(any())
            } returns 1
            repo.addBookmark(mockDetail).map {
                delay(100)
                it
            }.test {
                delay(150)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Loading)
                assertEquals(null, actualData.payload)
                coVerify(atLeast = 1) { ds.addBookmark(any()) }
            }
        }
    }

    @Test
    fun addBookmarkSuccess() {
        val mockProduct = mockk<Bookmark>(relaxed = true)
        every {
            mockProduct.movieId
        } returns "1"

        runTest {
            coEvery {
                ds.addBookmark(any())
            } returns 1
            repo.addBookmark(mockDetail).map {
                delay(100)
                it
            }.test {
                delay(250)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                assertEquals(true, actualData.payload)
                coVerify(atLeast = 1) { ds.addBookmark(any()) }
            }
        }
    }

    @Test
    fun addBookmarkError() {
        val mockProduct = mockk<Bookmark>(relaxed = true)
        every {
            mockProduct.movieId
        } returns "1"

        runTest {
            coEvery {
                ds.addBookmark(any())
            } throws IllegalStateException("Mock Error")
            repo.addBookmark(mockDetail).map {
                delay(100)
                it
            }.test {
                delay(250)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Error)
                coVerify(atLeast = 1) { ds.addBookmark(any()) }
            }
        }
    }

    @Test
    fun deleteBookmarkLoading() {
        coEvery {
            ds.deleteBookmark(any())
        } returns 1

        runTest {
            repo.deleteBookmark(mockBookmark).map {
                delay(100)
                it
            }.test {
                delay(150)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Loading)
                coVerify(atLeast = 1) { ds.deleteBookmark(any()) }
            }
        }
    }

    @Test
    fun deleteBookmarkSuccess() {
        coEvery {
            ds.deleteBookmark(any())
        } returns 1

        runTest {
            repo.deleteBookmark(mockBookmark).map {
                delay(100)
                it
            }.test {
                delay(250)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                coVerify(atLeast = 1) { ds.deleteBookmark(any()) }
            }
        }
    }

    @Test
    fun deleteBookmarkError() {
        coEvery {
            ds.deleteBookmark(any())
        } throws IllegalStateException("Mock Error")

        runTest {
            repo.deleteBookmark(mockBookmark).map {
                delay(100)
                it
            }.test {
                delay(250)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Error)
                coVerify(atLeast = 1) { ds.deleteBookmark(any()) }
            }
        }
    }

    @Test
    fun removeBookmarkLoading() {
        coEvery {
            ds.removeBookmark(any())
        } returns 1

        runTest {
            repo.removeBookmark(mockBookmark.movieId).map {
                delay(100)
                it
            }.test {
                delay(150)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Loading)
                coVerify(atLeast = 1) { ds.removeBookmark(any()) }
            }
        }
    }

    @Test
    fun removeBookmarkSuccess() {
        coEvery {
            ds.removeBookmark(any())
        } returns 1

        runTest {
            repo.removeBookmark(mockBookmark.movieId).map {
                delay(100)
                it
            }.test {
                delay(250)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                coVerify(atLeast = 1) { ds.removeBookmark(any()) }
            }
        }
    }

    @Test
    fun removeBookmarkError() {
        coEvery {
            ds.removeBookmark(any())
        } throws IllegalStateException("Mock Error")

        runTest {
            repo.removeBookmark(mockBookmark.movieId).map {
                delay(100)
                it
            }.test {
                delay(250)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Error)
                coVerify(atLeast = 1) { ds.removeBookmark(any()) }
            }
        }
    }

    @Test
    fun deleteAllLoading() {
        coEvery {
            ds.deleteAll()
        } returns Unit

        runTest {
            repo.deleteAll().map {
                delay(100)
                it
            }.test {
                delay(150)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Loading)
                coVerify(atLeast = 1) { ds.deleteAll() }
            }
        }
    }

    @Test
    fun deleteAllSuccess() {
        coEvery {
            ds.deleteAll()
        } returns Unit

        runTest {
            repo.deleteAll().map {
                delay(100)
                it
            }.test {
                delay(250)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                coVerify(atLeast = 1) { ds.deleteAll() }
            }
        }
    }

    @Test
    fun deleteAllError() {
        coEvery {
            ds.deleteAll()
        } throws IllegalStateException("Mock Error")

        runTest {
            repo.deleteAll().map {
                delay(100)
                it
            }.test {
                delay(250)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Error)
                coVerify(atLeast = 1) { ds.deleteAll() }
            }
        }
    }
}
