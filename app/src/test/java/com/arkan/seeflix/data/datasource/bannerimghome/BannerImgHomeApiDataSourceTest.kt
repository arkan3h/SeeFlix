package com.arkan.seeflix.data.datasource.bannerimghome

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

class BannerImgHomeApiDataSourceTest {
    @MockK
    lateinit var service: SeeflixApiServices

    private lateinit var ds: BannerImgHomeDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        ds = BannerImgHomeApiDataSource(service)
    }

    @Test
    fun getBannerImgHome() {
        runTest {
            val mockResponse = mockk<MoviesResponse>(relaxed = true)
            coEvery {
                service.getBannerImgHome()
            } returns mockResponse

            val actualResult = ds.getBannerImgHome()
            coVerify {
                service.getBannerImgHome()
            }
            assertEquals(actualResult, mockResponse)
        }
    }
}
