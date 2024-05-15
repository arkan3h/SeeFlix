package com.arkan.seeflix.presentation.listmovie

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.arkan.seeflix.data.repository.ListMovieRepository
import kotlinx.coroutines.Dispatchers

class ListMovieViewModel(
    private val extras: Bundle?,
    private val listMovieRepository: ListMovieRepository,
) : ViewModel() {
    val category = extras?.getInt(ListMovieActivity.CATEGORY_NAME)

    fun getListMovies(category: Int) = listMovieRepository.getListMovies(category).asLiveData(Dispatchers.IO)
}
