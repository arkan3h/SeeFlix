package com.arkan.seeflix.data.repository

import app.cash.turbine.test
import com.arkan.seeflix.data.datasource.detail.DetailMovieDataSource
import com.arkan.seeflix.data.source.network.model.MovieDetailResponse
import com.arkan.seeflix.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class DetailMovieRepositoryImplTest {
    @MockK
    lateinit var ds: DetailMovieDataSource

    private lateinit var repo: DetailMovieRepository

    private val mockResponse =
        MovieDetailResponse(
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
        repo = DetailMovieRepositoryImpl(ds)
    }

    @Test
    fun getDetailLoading() {
        runTest {
            coEvery {
                ds.getDetail(any())
            } returns mockResponse
            repo.getDetail("111").map {
                delay(100)
                it
            }.test {
                delay(150)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
                coVerify {
                    ds.getDetail(any())
                }
            }
        }
    }

    @Test
    fun getDetailSuccess() {
        runTest {
            coEvery {
                ds.getDetail(any())
            } returns mockResponse
            repo.getDetail("111").map {
                delay(100)
                it
            }.test {
                delay(250)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify {
                    ds.getDetail(any())
                }
            }
        }
    }

    @Test
    fun getDetailError() {
        runTest {
            coEvery {
                ds.getDetail(any())
            } throws IllegalStateException("Mock Error")
            repo.getDetail("111").map {
                delay(100)
                it
            }.test {
                delay(250)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                coVerify {
                    ds.getDetail(any())
                }
            }
        }
    }
}
