package com.arkan.seeflix.data.datasource.listmovie

import com.arkan.seeflix.data.source.network.model.MoviesResponse
import com.arkan.seeflix.data.source.network.services.SeeflixApiServices
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ListMovieApiDataSourceTest {
    @MockK
    lateinit var service: SeeflixApiServices

    private lateinit var ds: ListMovieDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        ds = ListMovieApiDataSource(service)
    }

    @Test
    fun getListMovies1() {
        runTest {
            val mockResponse = mockk<MoviesResponse>(relaxed = true)
            coEvery {
                service.getNowPlaying()
            } returns mockResponse

            val actualResult = ds.getListMovies(1)
            coVerify {
                service.getNowPlaying()
            }
            assertEquals(actualResult, mockResponse)
        }
    }

    @Test
    fun getListMovies2() {
        runTest {
            val mockResponse = mockk<MoviesResponse>(relaxed = true)
            coEvery {
                service.getPopular()
            } returns mockResponse

            val actualResult = ds.getListMovies(2)
            coVerify {
                service.getPopular()
            }
            assertEquals(actualResult, mockResponse)
        }
    }

    @Test
    fun getListMovies3() {
        runTest {
            val mockResponse = mockk<MoviesResponse>(relaxed = true)
            coEvery {
                service.getUpcoming()
            } returns mockResponse

            val actualResult = ds.getListMovies(3)
            coVerify {
                service.getUpcoming()
            }
            assertEquals(actualResult, mockResponse)
        }
    }

    @Test
    fun getListMovies4() {
        runTest {
            val mockResponse = mockk<MoviesResponse>(relaxed = true)
            coEvery {
                service.getTopRated()
            } returns mockResponse

            val actualResult = ds.getListMovies(4)
            coVerify {
                service.getTopRated()
            }
            assertEquals(actualResult, mockResponse)
        }
    }
}
