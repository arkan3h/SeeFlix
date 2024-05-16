package com.arkan.seeflix.data.datasource.toprated

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

class TopRatedApiDataSourceTest {
    @MockK
    lateinit var services: SeeflixApiServices

    private lateinit var ds: TopRatedDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        ds = TopRatedApiDataSource(services)
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
}
