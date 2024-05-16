package com.arkan.seeflix.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.arkan.seeflix.data.repository.BannerImgHomeRepository
import com.arkan.seeflix.data.repository.MovieRepository
import kotlinx.coroutines.Dispatchers

class HomeViewModel(
    private val bannerImgHomeRepository: BannerImgHomeRepository,
    private val movieRepository: MovieRepository,
) : ViewModel() {
    fun getBannerImgHome() = bannerImgHomeRepository.getBannerImgHome().asLiveData(Dispatchers.IO)

    fun getNowPlaying() = movieRepository.getNowPlaying().asLiveData(Dispatchers.IO)

    fun getPopular() = movieRepository.getPopular().asLiveData(Dispatchers.IO)

    fun getUpcoming() = movieRepository.getUpcoming().asLiveData(Dispatchers.IO)

    fun getTopRated() = movieRepository.getTopRated().asLiveData(Dispatchers.IO)
}
