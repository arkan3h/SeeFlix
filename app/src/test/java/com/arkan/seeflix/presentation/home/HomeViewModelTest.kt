package com.arkan.seeflix.presentation.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.arkan.seeflix.data.repository.BannerImgHomeRepository
import com.arkan.seeflix.data.repository.MovieRepository
import com.arkan.seeflix.tools.MainCoroutineRule
import com.arkan.seeflix.tools.getOrAwaitValue
import com.arkan.seeflix.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class HomeViewModelTest {
    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    lateinit var bannerRepo: BannerImgHomeRepository

    @MockK
    lateinit var movieRepo: MovieRepository

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = spyk(HomeViewModel(bannerRepo, movieRepo))
    }

    @Test
    fun getBannerImgHome() {
        every {
            bannerRepo.getBannerImgHome()
        } returns
            flow {
                emit(
                    ResultWrapper.Success(
                        listOf(
                            mockk(relaxed = true),
                            mockk(relaxed = true),
                        ),
                    ),
                )
            }
        val result = viewModel.getBannerImgHome().getOrAwaitValue()
        assertEquals(2, result.payload?.size)
    }

    @Test
    fun getNowPlaying() {
        every {
            movieRepo.getNowPlaying()
        } returns
            flow {
                emit(
                    ResultWrapper.Success(
                        listOf(
                            mockk(relaxed = true),
                            mockk(relaxed = true),
                        ),
                    ),
                )
            }
        val result = viewModel.getNowPlaying().getOrAwaitValue()
        assertEquals(2, result.payload?.size)
    }

    @Test
    fun getPopular() {
        every {
            movieRepo.getPopular()
        } returns
            flow {
                emit(
                    ResultWrapper.Success(
                        listOf(
                            mockk(relaxed = true),
                            mockk(relaxed = true),
                        ),
                    ),
                )
            }
        val result = viewModel.getPopular().getOrAwaitValue()
        assertEquals(2, result.payload?.size)
    }

    @Test
    fun getUpcoming() {
        every {
            movieRepo.getUpcoming()
        } returns
            flow {
                emit(
                    ResultWrapper.Success(
                        listOf(
                            mockk(relaxed = true),
                            mockk(relaxed = true),
                        ),
                    ),
                )
            }
        val result = viewModel.getUpcoming().getOrAwaitValue()
        assertEquals(2, result.payload?.size)
    }

    @Test
    fun getTopRated() {
        every {
            movieRepo.getTopRated()
        } returns
            flow {
                emit(
                    ResultWrapper.Success(
                        listOf(
                            mockk(relaxed = true),
                            mockk(relaxed = true),
                        ),
                    ),
                )
            }
        val result = viewModel.getTopRated().getOrAwaitValue()
        assertEquals(2, result.payload?.size)
    }
}
