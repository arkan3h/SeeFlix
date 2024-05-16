package com.arkan.seeflix.data.datasource.popular

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

class PopularApiDataSourceTest {
    @MockK
    lateinit var services: SeeflixApiServices

    private lateinit var ds: PopularDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        ds = PopularApiDataSource(services)
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
}
