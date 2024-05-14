package com.arkan.seeflix.presentation.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.arkan.seeflix.data.repository.BookmarkRepository
import kotlinx.coroutines.Dispatchers

class BookmarkViewModel(
    private val bookmarkRepository: BookmarkRepository,
) : ViewModel() {
    fun getAllBookmark() = bookmarkRepository.getAllBookmark().asLiveData(Dispatchers.IO)
}
