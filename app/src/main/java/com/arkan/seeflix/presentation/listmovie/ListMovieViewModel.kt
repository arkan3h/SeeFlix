package com.arkan.seeflix.presentation.listmovie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.arkan.seeflix.data.repository.ListMovieRepository
import kotlinx.coroutines.Dispatchers

class ListMovieViewModel(
    private val listMovieRepository: ListMovieRepository,
) : ViewModel() {
    fun getListMovies(category: Int) = listMovieRepository.getListMovies(category).asLiveData(Dispatchers.IO)
}
