package com.arkan.seeflix.presentation.listmovie

import android.os.Bundle
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.arkan.seeflix.data.repository.ListMovieRepository
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

class ListMovieViewModelTest {
    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    lateinit var repo: ListMovieRepository

    @MockK
    lateinit var extras: Bundle

    private lateinit var viewModel: ListMovieViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        every { extras.getInt(ListMovieActivity.CATEGORY_NAME) } returns 1
        viewModel = spyk(ListMovieViewModel(extras, repo))
    }

    @Test
    fun getListMovies() {
        every {
            repo.getListMovies(any())
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
        val result = viewModel.getListMovies(1).getOrAwaitValue()
        assertEquals(2, result.payload?.size)
    }
}
