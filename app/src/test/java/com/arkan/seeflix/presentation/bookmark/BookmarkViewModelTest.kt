package com.arkan.seeflix.presentation.bookmark

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.arkan.seeflix.data.repository.BookmarkRepository
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

class BookmarkViewModelTest {
    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    lateinit var repo: BookmarkRepository

    private lateinit var viewModel: BookmarkViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = spyk(BookmarkViewModel(repo))
    }

    @Test
    fun getAllBookmark() {
        every {
            repo.getAllBookmark()
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
        val result = viewModel.getAllBookmark().getOrAwaitValue()
        assertEquals(2, result.payload?.size)
    }
}
