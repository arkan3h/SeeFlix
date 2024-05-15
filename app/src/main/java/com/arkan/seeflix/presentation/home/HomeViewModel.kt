package com.arkan.seeflix.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.arkan.seeflix.data.repository.BannerImgHomeRepository
import com.arkan.seeflix.data.repository.NowPlayingRepository
import com.arkan.seeflix.data.repository.PopularRepository
import com.arkan.seeflix.data.repository.TopRatedRepository
import com.arkan.seeflix.data.repository.UpcomingRepository
import kotlinx.coroutines.Dispatchers

class HomeViewModel(
    private val bannerImgHomeRepository: BannerImgHomeRepository,
    private val nowPlayingRepository: NowPlayingRepository,
    private val popularRepository: PopularRepository,
    private val upcomingRepository: UpcomingRepository,
    private val topRatedRepository: TopRatedRepository,
) : ViewModel() {
    fun getBannerImgHome() = bannerImgHomeRepository.getBannerImgHome().asLiveData(Dispatchers.IO)

    fun getNowPlaying() = nowPlayingRepository.getNowPlaying().asLiveData(Dispatchers.IO)

    fun getPopular() = popularRepository.getPopular().asLiveData(Dispatchers.IO)

    fun getUpcoming() = upcomingRepository.getUpcoming().asLiveData(Dispatchers.IO)

    fun getTopRated() = topRatedRepository.getTopRated().asLiveData(Dispatchers.IO)
}
