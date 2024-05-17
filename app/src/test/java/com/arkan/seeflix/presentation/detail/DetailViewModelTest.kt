package com.arkan.seeflix.presentation.detail

import android.os.Bundle
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.arkan.seeflix.data.model.Detail
import com.arkan.seeflix.data.repository.BookmarkRepository
import com.arkan.seeflix.data.repository.DetailMovieRepository
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

class DetailViewModelTest {
    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    lateinit var detailRepository: DetailMovieRepository

    @MockK
    lateinit var bookmarkRepository: BookmarkRepository

    @MockK
    lateinit var extras: Bundle

    private lateinit var viewModel: DetailViewModel

    private val mockDetail = mockk<Detail>(relaxed = true)

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        every { extras.getString(DetailActivity.DETAIL_ID) } returns "1"
        viewModel = spyk(DetailViewModel(extras, detailRepository, bookmarkRepository))
    }

    @Test
    fun getDetailData() {
        every {
            detailRepository.getDetail(any())
        } returns
            flow {
                emit(
                    ResultWrapper.Success(
                        mockDetail,
                    ),
                )
            }
        val result = viewModel.getDetailData("1").getOrAwaitValue()
        assertEquals(mockDetail.id, result.payload?.id)
    }

    @Test
    fun addBookmark() {
        every {
            bookmarkRepository.addBookmark(any())
        } returns
            flow {
                emit(
                    ResultWrapper.Success(
                        true,
                    ),
                )
            }
        val result = viewModel.addBookmark(mockDetail).getOrAwaitValue()
        assertEquals(true, result.payload)
    }

    @Test
    fun checkMovieBookmark() {
        every {
            bookmarkRepository.checkBookmarkById(any())
        } returns
            flow {
                emit(
                    listOf(
                        mockk(relaxed = true),
                        mockk(relaxed = true),
                    ),
                )
            }
        val result = viewModel.checkMovieBookmark("1").getOrAwaitValue()
        assertEquals(2, result.size)
    }

    @Test
    fun removeBookmark() {
        every {
            bookmarkRepository.removeBookmark(any())
        } returns
            flow {
                emit(
                    ResultWrapper.Success(
                        true,
                    ),
                )
            }
        val result = viewModel.removeBookmark("1").getOrAwaitValue()
        assertEquals(true, result.payload)
    }
}
