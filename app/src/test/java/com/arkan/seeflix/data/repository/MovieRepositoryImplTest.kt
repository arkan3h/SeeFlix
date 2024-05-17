package com.arkan.seeflix.data.repository

import app.cash.turbine.test
import com.arkan.seeflix.data.datasource.movie.MovieDataSource
import com.arkan.seeflix.data.source.network.model.MovieItemResponse
import com.arkan.seeflix.data.source.network.model.MoviesResponse
import com.arkan.seeflix.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class MovieRepositoryImplTest {
    @MockK
    lateinit var ds: MovieDataSource

    private lateinit var repo: MovieRepository

    private val item1 =
        MovieItemResponse(
            id = 1,
            title = "title",
            posterPath = "imgurl",
            overview = "overview",
        )
    private val item2 =
        MovieItemResponse(
            id = 2,
            title = "title2",
            posterPath = "imgurl2",
            overview = "overview2",
        )
    private val mockMovieItem = listOf(item1, item2)

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repo = MovieRepositoryImpl(ds)
    }

    @Test
    fun getNowPlayingLoading() {
        val mockResponse = mockk<MoviesResponse>()
        every {
            mockResponse.results
        } returns mockMovieItem

        runTest {
            coEvery {
                ds.getNowPlaying()
            } returns mockResponse
            repo.getNowPlaying().map {
                delay(100)
                it
            }.test {
                delay(150)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
                coVerify {
                    ds.getNowPlaying()
                }
            }
        }
    }

    @Test
    fun getNowPlayingSuccess() {
        val mockResponse = mockk<MoviesResponse>()
        every {
            mockResponse.results
        } returns mockMovieItem

        runTest {
            coEvery {
                ds.getNowPlaying()
            } returns mockResponse
            repo.getNowPlaying().map {
                delay(100)
                it
            }.test {
                delay(250)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify {
                    ds.getNowPlaying()
                }
            }
        }
    }

    @Test
    fun getNowPlayingError() {
        runTest {
            coEvery {
                ds.getNowPlaying()
            } throws IllegalStateException("Mock Error")
            repo.getNowPlaying().map {
                delay(100)
                it
            }.test {
                delay(250)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                coVerify {
                    ds.getNowPlaying()
                }
            }
        }
    }

    @Test
    fun getNowPlayingEmpty() {
        val mockMovieItem = listOf<MovieItemResponse>()
        val mockResponse = mockk<MoviesResponse>()
        every {
            mockResponse.results
        } returns mockMovieItem

        runTest {
            coEvery {
                ds.getNowPlaying()
            } returns mockResponse
            repo.getNowPlaying().map {
                delay(100)
                it
            }.test {
                delay(250)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Empty)
                coVerify {
                    ds.getNowPlaying()
                }
            }
        }
    }

    @Test
    fun getPopularLoading() {
        val mockResponse = mockk<MoviesResponse>()
        every {
            mockResponse.results
        } returns mockMovieItem

        runTest {
            coEvery {
                ds.getPopular()
            } returns mockResponse
            repo.getPopular().map {
                delay(100)
                it
            }.test {
                delay(150)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
                coVerify {
                    ds.getPopular()
                }
            }
        }
    }

    @Test
    fun getPopularSuccess() {
        val mockResponse = mockk<MoviesResponse>()
        every {
            mockResponse.results
        } returns mockMovieItem

        runTest {
            coEvery {
                ds.getPopular()
            } returns mockResponse
            repo.getPopular().map {
                delay(100)
                it
            }.test {
                delay(250)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify {
                    ds.getPopular()
                }
            }
        }
    }

    @Test
    fun getPopularError() {
        runTest {
            coEvery {
                ds.getPopular()
            } throws IllegalStateException("Mock Error")
            repo.getPopular().map {
                delay(100)
                it
            }.test {
                delay(250)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                coVerify {
                    ds.getPopular()
                }
            }
        }
    }

    @Test
    fun getPopularEmpty() {
        val mockMovieItem = listOf<MovieItemResponse>()
        val mockResponse = mockk<MoviesResponse>()
        every {
            mockResponse.results
        } returns mockMovieItem

        runTest {
            coEvery {
                ds.getPopular()
            } returns mockResponse
            repo.getPopular().map {
                delay(100)
                it
            }.test {
                delay(250)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Empty)
                coVerify {
                    ds.getPopular()
                }
            }
        }
    }

    @Test
    fun getTopRatedLoading() {
        val mockResponse = mockk<MoviesResponse>()
        every {
            mockResponse.results
        } returns mockMovieItem

        runTest {
            coEvery {
                ds.getTopRated()
            } returns mockResponse
            repo.getTopRated().map {
                delay(100)
                it
            }.test {
                delay(150)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
                coVerify {
                    ds.getTopRated()
                }
            }
        }
    }

    @Test
    fun getTopRatedSuccess() {
        val mockResponse = mockk<MoviesResponse>()
        every {
            mockResponse.results
        } returns mockMovieItem

        runTest {
            coEvery {
                ds.getTopRated()
            } returns mockResponse
            repo.getTopRated().map {
                delay(100)
                it
            }.test {
                delay(250)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify {
                    ds.getTopRated()
                }
            }
        }
    }

    @Test
    fun getTopRatedError() {
        runTest {
            coEvery {
                ds.getTopRated()
            } throws IllegalStateException("Mock Error")
            repo.getTopRated().map {
                delay(100)
                it
            }.test {
                delay(250)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                coVerify {
                    ds.getTopRated()
                }
            }
        }
    }

    @Test
    fun getTopRatedEmpty() {
        val mockMovieItem = listOf<MovieItemResponse>()
        val mockResponse = mockk<MoviesResponse>()
        every {
            mockResponse.results
        } returns mockMovieItem

        runTest {
            coEvery {
                ds.getTopRated()
            } returns mockResponse
            repo.getTopRated().map {
                delay(100)
                it
            }.test {
                delay(250)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Empty)
                coVerify {
                    ds.getTopRated()
                }
            }
        }
    }

    @Test
    fun getUpcomingLoading() {
        val mockResponse = mockk<MoviesResponse>()
        every {
            mockResponse.results
        } returns mockMovieItem

        runTest {
            coEvery {
                ds.getUpcoming()
            } returns mockResponse
            repo.getUpcoming().map {
                delay(100)
                it
            }.test {
                delay(150)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
                coVerify {
                    ds.getUpcoming()
                }
            }
        }
    }

    @Test
    fun getUpcomingSuccess() {
        val mockResponse = mockk<MoviesResponse>()
        every {
            mockResponse.results
        } returns mockMovieItem

        runTest {
            coEvery {
                ds.getUpcoming()
            } returns mockResponse
            repo.getUpcoming().map {
                delay(100)
                it
            }.test {
                delay(250)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify {
                    ds.getUpcoming()
                }
            }
        }
    }

    @Test
    fun getUpcomingError() {
        runTest {
            coEvery {
                ds.getUpcoming()
            } throws IllegalStateException("Mock Error")
            repo.getUpcoming().map {
                delay(100)
                it
            }.test {
                delay(250)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                coVerify {
                    ds.getUpcoming()
                }
            }
        }
    }

    @Test
    fun getUpcomingEmpty() {
        val mockMovieItem = listOf<MovieItemResponse>()
        val mockResponse = mockk<MoviesResponse>()
        every {
            mockResponse.results
        } returns mockMovieItem

        runTest {
            coEvery {
                ds.getUpcoming()
            } returns mockResponse
            repo.getUpcoming().map {
                delay(100)
                it
            }.test {
                delay(250)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Empty)
                coVerify {
                    ds.getUpcoming()
                }
            }
        }
    }
}
