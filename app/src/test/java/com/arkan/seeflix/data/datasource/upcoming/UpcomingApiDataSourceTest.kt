package com.arkan.seeflix.data.datasource.upcoming

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

class UpcomingApiDataSourceTest {
    @MockK
    lateinit var services: SeeflixApiServices

    private lateinit var ds: UpcomingDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        ds = UpcomingApiDataSource(services)
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
