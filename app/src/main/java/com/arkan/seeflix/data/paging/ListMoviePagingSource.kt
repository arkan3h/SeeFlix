package com.arkan.seeflix.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.arkan.seeflix.data.mapper.toMovie
import com.arkan.seeflix.data.model.Movie
import com.arkan.seeflix.data.source.network.services.SeeflixApiServices
import okio.IOException
import retrofit2.HttpException

private const val TMDB_STARTING_PAGE_INDEX = 1

class ListMoviePagingSource(
    private val service: SeeflixApiServices,
) : PagingSource<Int, Movie>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val pageIndex = params.key ?: TMDB_STARTING_PAGE_INDEX
        return try {
            val response =
                service.getTopRated(
                    language = "en-US",
                    page = pageIndex,
                )
            val movies = response.results.toMovie()
            val nextKey = if (movies.isEmpty()) null else pageIndex + 1
            LoadResult.Page(
                data = movies,
                prevKey = if (pageIndex == TMDB_STARTING_PAGE_INDEX) null else pageIndex,
                nextKey = nextKey,
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
