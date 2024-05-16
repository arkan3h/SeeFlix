package com.arkan.seeflix.data.repository.movie

import com.arkan.seeflix.data.datasource.movie.MovieDataSource
import com.arkan.seeflix.data.mapper.toMovieDetail
import com.arkan.seeflix.data.model.MovieDetail
import com.arkan.seeflix.utils.ResultWrapper
import com.arkan.seeflix.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

class MovieRepositoryImpl(private val dataSource: MovieDataSource
) : MovieRepository {
    override fun getDetail(id: String): Flow<ResultWrapper<MovieDetail>> {
        return proceedFlow {
            dataSource.getDetail(id).toMovieDetail()
        }
    }
}

