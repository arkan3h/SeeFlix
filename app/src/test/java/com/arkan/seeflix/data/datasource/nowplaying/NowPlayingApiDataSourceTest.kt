package com.arkan.seeflix.data.datasource.nowplaying

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

class NowPlayingApiDataSourceTest {
    @MockK
    lateinit var services: SeeflixApiServices

    private lateinit var ds: NowPlayingDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        ds = NowPlayingApiDataSource(services)
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
}
