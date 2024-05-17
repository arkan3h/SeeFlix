package com.arkan.seeflix.presentation.detail

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.arkan.seeflix.data.model.Detail
import com.arkan.seeflix.data.repository.BookmarkRepository
import com.arkan.seeflix.data.repository.DetailMovieRepository
import kotlinx.coroutines.Dispatchers

class DetailViewModel(
    private val extras: Bundle?,
    private val detailMovieRepository: DetailMovieRepository,
    private val bookmarkRepository: BookmarkRepository,
) : ViewModel() {
    val idMovie = extras?.getString(DetailActivity.DETAIL_ID)

    fun getDetailData(id: String) = detailMovieRepository.getDetail(id).asLiveData(Dispatchers.IO)

    fun addBookmark(detail: Detail) = bookmarkRepository.addBookmark(detail).asLiveData(Dispatchers.IO)

    fun checkMovieBookmark(id: String) = bookmarkRepository.checkBookmarkById(id).asLiveData(Dispatchers.IO)

    fun removeBookmark(id: String) = bookmarkRepository.removeBookmark(id).asLiveData(Dispatchers.IO)
}
