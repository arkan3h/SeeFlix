package com.arkan.seeflix.data.repository

import app.cash.turbine.test
import com.arkan.seeflix.data.datasource.upcoming.UpcomingDataSource
import com.arkan.seeflix.data.source.network.model.MovieItemResponse
import com.arkan.seeflix.data.source.network.model.MoviesResponse
import com.arkan.seeflix.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class UpcomingRepositoryImplTest {
    @MockK
    lateinit var ds: UpcomingDataSource

    private lateinit var repo: UpcomingRepository

    private val item1 =
        MovieItemResponse(
            id = 1,
            title = "title",
            posterPath = "imgurl",
            overview = "overview",
        )
    private val item2 =
        MovieItemResponse(
            id = 2,
            title = "title2",
            posterPath = "imgurl2",
            overview = "overview2",
        )
    private val mockMovieItem = listOf(item1, item2)

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repo = UpcomingRepositoryImpl(ds)
    }

    @Test
    fun getUpcomingLoading() {
        val mockResponse = mockk<MoviesResponse>()
        every {
            mockResponse.results
        } returns mockMovieItem

        runTest {
            coEvery {
                ds.getUpcoming()
            } returns mockResponse
            repo.getUpcoming().map {
                delay(100)
                it
            }.test {
                delay(150)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
                coVerify {
                    ds.getUpcoming()
                }
            }
        }
    }

    @Test
    fun getUpcomingSuccess() {
        val mockResponse = mockk<MoviesResponse>()
        every {
            mockResponse.results
        } returns mockMovieItem

        runTest {
            coEvery {
                ds.getUpcoming()
            } returns mockResponse
            repo.getUpcoming().map {
                delay(100)
                it
            }.test {
                delay(250)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify {
                    ds.getUpcoming()
                }
            }
        }
    }

    @Test
    fun getUpcomingError() {
        runTest {
            coEvery {
                ds.getUpcoming()
            } throws IllegalStateException("Mock Error")
            repo.getUpcoming().map {
                delay(100)
                it
            }.test {
                delay(250)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                coVerify {
                    ds.getUpcoming()
                }
            }
        }
    }

    @Test
    fun getUpcomingEmpty() {
        val mockMovieItem = listOf<MovieItemResponse>()
        val mockResponse = mockk<MoviesResponse>()
        every {
            mockResponse.results
        } returns mockMovieItem

        runTest {
            coEvery {
                ds.getUpcoming()
            } returns mockResponse
            repo.getUpcoming().map {
                delay(100)
                it
            }.test {
                delay(250)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Empty)
                coVerify {
                    ds.getUpcoming()
                }
            }
        }
    }
}
