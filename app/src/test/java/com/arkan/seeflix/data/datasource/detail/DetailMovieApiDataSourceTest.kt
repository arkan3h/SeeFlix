package com.arkan.seeflix.data.datasource.detail

import com.arkan.seeflix.data.source.network.model.MovieDetailResponse
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

class DetailMovieApiDataSourceTest {
    @MockK
    lateinit var services: SeeflixApiServices

    private lateinit var ds: DetailMovieDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        ds = DetailMovieApiDataSource(services)
    }

    @Test
    fun getDetail() {
        runTest {
            val mockResponse = mockk<MovieDetailResponse>(relaxed = true)
            coEvery {
                services.getMovieDetail(any())
            } returns mockResponse

            val actualResult = ds.getDetail("111")
            coVerify {
                services.getMovieDetail(any())
            }
            assertEquals(actualResult, mockResponse)
        }
    }
}
