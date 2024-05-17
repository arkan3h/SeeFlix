package com.arkan.seeflix.data.datasource.movie

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

class MovieApiDataSourceTest {
    @MockK
    lateinit var services: SeeflixApiServices

    private lateinit var ds: MovieDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        ds = MovieApiDataSource(services)
    }

    @Test
    fun getNowPlaying() {
        runTest {
            val mockResponse = mockk<MoviesResponse>(relaxed = true)
            coEvery {
                services.getNowPlaying()
            } returns mockResponse

            val actualResult = ds.getNowPlaying()
            coVerify {
                services.getNowPlaying()
            }
            assertEquals(actualResult, mockResponse)
        }
    }

    @Test
    fun getPopular() {
        runTest {
            val mockResponse = mockk<MoviesResponse>(relaxed = true)
            coEvery {
                services.getPopular()
            } returns mockResponse

            val actualResult = ds.getPopular()
            coVerify {
                services.getPopular()
            }
            assertEquals(actualResult, mockResponse)
        }
    }

    @Test
    fun getTopRated() {
        runTest {
            val mockResponse = mockk<MoviesResponse>(relaxed = true)
            coEvery {
                services.getTopRated()
            } returns mockResponse

            val actualResult = ds.getTopRated()
            coVerify {
                services.getTopRated()
            }
            assertEquals(actualResult, mockResponse)
        }
    }

    @Test
    fun getUpcoming() {
        runTest {
            val mockResponse = mockk<MoviesResponse>(relaxed = true)
            coEvery {
                services.getUpcoming()
            } returns mockResponse

            val actualResult = ds.getUpcoming()
            coVerify {
                services.getUpcoming()
            }
            assertEquals(actualResult, mockResponse)
        }
    }
}
