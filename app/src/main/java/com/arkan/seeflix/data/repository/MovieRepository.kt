package com.arkan.seeflix.data.repository

import com.arkan.seeflix.data.datasource.movie.MovieDataSource
import com.arkan.seeflix.data.mapper.toMovie
import com.arkan.seeflix.data.model.Movie
import com.arkan.seeflix.utils.ResultWrapper
import com.arkan.seeflix.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getNowPlaying(): Flow<ResultWrapper<List<Movie>>>

    fun getTopRated(): Flow<ResultWrapper<List<Movie>>>

    fun getUpcoming(): Flow<ResultWrapper<List<Movie>>>

    fun getPopular(): Flow<ResultWrapper<List<Movie>>>
}

class MovieRepositoryImpl(
    private val dataSource: MovieDataSource,
) : MovieRepository {
    override fun getNowPlaying(): Flow<ResultWrapper<List<Movie>>> {
        return proceedFlow {
            dataSource.getNowPlaying().results.toMovie()
        }
    }

    override fun getTopRated(): Flow<ResultWrapper<List<Movie>>> {
        return proceedFlow {
            dataSource.getTopRated().results.toMovie()
        }
    }

    override fun getUpcoming(): Flow<ResultWrapper<List<Movie>>> {
        return proceedFlow {
            dataSource.getUpcoming().results.toMovie()
        }
    }

    override fun getPopular(): Flow<ResultWrapper<List<Movie>>> {
        return proceedFlow {
            dataSource.getPopular().results.toMovie()
        }
    }
}
