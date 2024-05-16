package com.arkan.seeflix.presentation.listmovie

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.arkan.seeflix.data.model.Movie
import com.arkan.seeflix.data.repository.ListMovieRepository
import kotlinx.coroutines.flow.Flow

class ListMovieViewModel(
    private val extras: Bundle?,
    private val repo: ListMovieRepository,
) : ViewModel() {
    val category = extras?.getInt(ListMovieActivity.CATEGORY)

    fun getListTopRated(): Flow<PagingData<Movie>> = repo.getListTopRated().cachedIn(viewModelScope)

    fun getListPopular(): Flow<PagingData<Movie>> = repo.getListPopular().cachedIn(viewModelScope)

    fun getListNowPlaying(): Flow<PagingData<Movie>> = repo.getListNowPlaying().cachedIn(viewModelScope)

    fun getListUpcoming(): Flow<PagingData<Movie>> = repo.getListUpcoming().cachedIn(viewModelScope)
}
