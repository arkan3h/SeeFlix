package com.arkan.seeflix.presentation.detail


import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.arkan.seeflix.data.repository.movie.MovieRepository
import kotlinx.coroutines.Dispatchers

class ViewModelDetailActivity(
    private val movieRepository: MovieRepository,
    private val intent: Intent
) : ViewModel() {
    val id: String? = intent.getStringExtra(DetailActivity.ID_MOVIE)

    val link: String
        get() = "https://api.themoviedb.org/3/movie/$id"
    val linkImg: String
        get() = "https://image.tmdb.org/t/p/w500"

    fun getDetail(id: String) = movieRepository.getDetail(id).asLiveData(Dispatchers.IO)
}
