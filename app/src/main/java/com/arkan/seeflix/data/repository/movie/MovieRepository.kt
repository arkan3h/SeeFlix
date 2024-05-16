package com.arkan.seeflix.data.repository.movie

import com.arkan.seeflix.data.model.MovieDetail
import com.arkan.seeflix.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getDetail(id: String): Flow<ResultWrapper<MovieDetail>>
}
