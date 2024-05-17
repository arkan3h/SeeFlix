package com.arkan.seeflix.data.repository

import app.cash.turbine.test
import com.arkan.seeflix.data.datasource.listmovie.ListMovieDataSource
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

class ListMovieRepositoryImplTest {
    @MockK
    lateinit var ds: ListMovieDataSource

    private lateinit var repo: ListMovieRepository

    private val item1 =
        MovieItemResponse(
            id = "1",
            title = "title",
            posterPath = "imgurl",
            overview = "overview",
        )
    private val item2 =
        MovieItemResponse(
            id = "2",
            title = "title2",
            posterPath = "imgurl2",
            overview = "overview2",
        )
    private val mockMovieItem = listOf(item1, item2)

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repo = ListMovieRepositoryImpl(ds)
    }

    @Test
    fun getListMoviesLoading() {
        val mockResponse = mockk<MoviesResponse>()
        every {
            mockResponse.results
        } returns mockMovieItem

        runTest {
            coEvery {
                ds.getListMovies(any())
            } returns mockResponse
            repo.getListMovies(1).map {
                delay(100)
                it
            }.test {
                delay(150)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
                coVerify {
                    ds.getListMovies(any())
                }
            }
        }
    }

    @Test
    fun getListMoviesSuccess() {
        val mockResponse = mockk<MoviesResponse>()
        every {
            mockResponse.results
        } returns mockMovieItem

        runTest {
            coEvery {
                ds.getListMovies(any())
            } returns mockResponse
            repo.getListMovies(1).map {
                delay(100)
                it
            }.test {
                delay(250)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify {
                    ds.getListMovies(any())
                }
            }
        }
    }

    @Test
    fun getListMoviesError() {
        runTest {
            coEvery {
                ds.getListMovies(any())
            } throws IllegalStateException("Mock Error")
            repo.getListMovies(1).map {
                delay(100)
                it
            }.test {
                delay(250)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                coVerify {
                    ds.getListMovies(any())
                }
            }
        }
    }

    @Test
    fun getListMoviesEmpty() {
        val mockMovieItem = listOf<MovieItemResponse>()
        val mockResponse = mockk<MoviesResponse>()
        every {
            mockResponse.results
        } returns mockMovieItem

        runTest {
            coEvery {
                ds.getListMovies(any())
            } returns mockResponse
            repo.getListMovies(1).map {
                delay(100)
                it
            }.test {
                delay(250)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Empty)
                coVerify {
                    ds.getListMovies(any())
                }
            }
        }
    }
}
