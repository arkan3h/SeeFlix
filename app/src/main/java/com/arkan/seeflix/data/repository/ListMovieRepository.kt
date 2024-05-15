package com.arkan.seeflix.data.repository

import com.arkan.seeflix.data.datasource.listmovie.ListMovieDataSource
import com.arkan.seeflix.data.mapper.toListMovies
import com.arkan.seeflix.data.model.Movie
import com.arkan.seeflix.utils.ResultWrapper
import com.arkan.seeflix.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface ListMovieRepository {
    fun getListMovies(category: Int): Flow<ResultWrapper<List<Movie>>>
}

class ListMovieRepositoryImpl(private val dataSource: ListMovieDataSource) : ListMovieRepository {
    override fun getListMovies(category: Int): Flow<ResultWrapper<List<Movie>>> {
        return proceedFlow { dataSource.getListMovies(category).results.toListMovies() }
    }
}
