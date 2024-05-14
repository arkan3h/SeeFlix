package com.arkan.seeflix.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.arkan.seeflix.data.repository.NowPlayingRepository
import kotlinx.coroutines.Dispatchers

class HomeViewModel(
    private val nowPlayingRepository: NowPlayingRepository,
//    private val popularRepository: PopularRepository,
//    private val upcomingRepository: UpcomingRepository,
//    private val topRatedRepository: TopRatedRepository
) : ViewModel() {
    fun getNowPlaying() = nowPlayingRepository.getNowPlaying().asLiveData(Dispatchers.IO)
//    fun getPopular() = popularRepository.getPopular().asLiveData(Dispatchers.IO)
//    fun getUpcoming() = upcomingRepository.getUpcoming().asLiveData(Dispatchers.IO)
//    fun getTopRated() = topRatedRepository.getTopRated().asLiveData(Dispatchers.IO)
}
