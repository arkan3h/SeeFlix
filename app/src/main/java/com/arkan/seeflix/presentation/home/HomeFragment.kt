package com.arkan.seeflix.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.load
import com.arkan.seeflix.R
import com.arkan.seeflix.data.datasource.bannerimghome.BannerImgHomeApiDataSource
import com.arkan.seeflix.data.datasource.nowplaying.NowPlayingApiDataSource
import com.arkan.seeflix.data.datasource.popular.PopularApiDataSource
import com.arkan.seeflix.data.datasource.toprated.TopRatedApiDataSource
import com.arkan.seeflix.data.datasource.upcoming.UpcomingApiDataSource
import com.arkan.seeflix.data.model.BannerImgHome
import com.arkan.seeflix.data.model.NowPlaying
import com.arkan.seeflix.data.model.Popular
import com.arkan.seeflix.data.model.TopRated
import com.arkan.seeflix.data.model.Upcoming
import com.arkan.seeflix.data.repository.BannerImgHomeRepository
import com.arkan.seeflix.data.repository.BannerImgHomeRepositoryImpl
import com.arkan.seeflix.data.repository.NowPlayingRepository
import com.arkan.seeflix.data.repository.NowPlayingRepositoryImpl
import com.arkan.seeflix.data.repository.PopularRepository
import com.arkan.seeflix.data.repository.PopularRepositoryImpl
import com.arkan.seeflix.data.repository.TopRatedRepository
import com.arkan.seeflix.data.repository.TopRatedRepositoryImpl
import com.arkan.seeflix.data.repository.UpcomingRepository
import com.arkan.seeflix.data.repository.UpcomingRepositoryImpl
import com.arkan.seeflix.data.source.network.services.SeeflixApiServices
import com.arkan.seeflix.databinding.FragmentHomeBinding
import com.arkan.seeflix.presentation.home.adapter.NowPlayingAdapter
import com.arkan.seeflix.presentation.home.adapter.PopularAdapter
import com.arkan.seeflix.presentation.home.adapter.TopRatedAdapter
import com.arkan.seeflix.presentation.home.adapter.UpcomingAdapter
import com.arkan.seeflix.presentation.listmovie.ListMovieActivity
import com.arkan.seeflix.utils.GenericViewModelFactory
import com.arkan.seeflix.utils.proceedWhen

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels {
        val service = SeeflixApiServices.invoke()

        val bannerImgHomeDataSource = BannerImgHomeApiDataSource(service)
        val bannerImgHomeRepository: BannerImgHomeRepository =
            BannerImgHomeRepositoryImpl(bannerImgHomeDataSource)

        val nowPlayingDataSource = NowPlayingApiDataSource(service)
        val nowPlayingRepository: NowPlayingRepository =
            NowPlayingRepositoryImpl(nowPlayingDataSource)

        val popularDataSource = PopularApiDataSource(service)
        val popularRepository: PopularRepository = PopularRepositoryImpl(popularDataSource)

        val upcomingDataSource = UpcomingApiDataSource(service)
        val upcomingRepository: UpcomingRepository = UpcomingRepositoryImpl(upcomingDataSource)

        val topRatedDataSource = TopRatedApiDataSource(service)
        val topRatedRepository: TopRatedRepository = TopRatedRepositoryImpl(topRatedDataSource)

        GenericViewModelFactory.create(
            HomeViewModel(
                bannerImgHomeRepository,
                nowPlayingRepository,
                popularRepository,
                upcomingRepository,
                topRatedRepository,
            ),
        )
    }

    private val nowPlayingAdapter: NowPlayingAdapter by lazy {
        NowPlayingAdapter {
//            DetailActivity.startActivity(requireContext(), it.id)
        }
    }

    private val popularAdapter: PopularAdapter by lazy {
        PopularAdapter {
//            DetailActivity.startActivity(requireContext(), it.id)
        }
    }

    private val upcomingAdapter: UpcomingAdapter by lazy {
        UpcomingAdapter {
//            DetailActivity.startActivity(requireContext(), it.id)
        }
    }

    private val topRatedAdapter: TopRatedAdapter by lazy {
        TopRatedAdapter {
//            DetailActivity.startActivity(requireContext(), it.id)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        setClickAction()
        navigateToListMovie()
        setupListData()
        getBannerImgRandom()
        getNowPlayingData()
        getPopularData()
        getUpcomingData()
        getTopRatedData()
    }

    private fun setClickAction() {
        binding.layoutBannerHome.ivInfoBanner.setOnClickListener {
            Toast.makeText(requireContext(), "Info Ditekan", Toast.LENGTH_SHORT).show()
        }

        binding.layoutBannerHome.ivShareBanner.setOnClickListener {
            Toast.makeText(requireContext(), "Share Ditekan", Toast.LENGTH_SHORT).show()
        }
    }

    private fun navigateToListMovie() {
        binding.ivListGridNowPlaying.setOnClickListener {
            ListMovieActivity.startActivity(requireContext(), 1)
        }

        binding.ivListGridPopular.setOnClickListener {
            ListMovieActivity.startActivity(requireContext(), 2)
        }

        binding.ivListGridUpcoming.setOnClickListener {
            ListMovieActivity.startActivity(requireContext(), 3)
        }

        binding.ivListGridTopRated.setOnClickListener {
            ListMovieActivity.startActivity(requireContext(), 4)
        }
    }

    private fun setupListData() {
        binding.rvNowPlaying.apply {
            adapter = nowPlayingAdapter
        }
        binding.rvPopular.apply {
            adapter = popularAdapter
        }
        binding.rvUpcoming.apply {
            adapter = upcomingAdapter
        }
        binding.rvTopRated.apply {
            adapter = topRatedAdapter
        }
    }

    private fun getBannerImgRandom() {
        viewModel.getBannerImgHome().observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    it.payload?.let { data ->
                        val dataRandom = setupBannerImgRandom(data)
                        bindBannerImgRandomData(dataRandom)
                    }
                    binding.layoutState.root.isVisible = false
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = false
                },
                doOnLoading = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = true
                    binding.layoutState.tvError.isVisible = false
//                    binding.layoutFav.root.isVisible = false
                },
                doOnError = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = true
                    binding.layoutState.tvError.text = it.exception?.message.orEmpty()
                },
                doOnEmpty = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = true
                    binding.layoutState.tvError.text =
                        getString(R.string.text_data_not_available)
                },
            )
        }
    }

    private fun getNowPlayingData() {
        viewModel.getNowPlaying().observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    it.payload?.let { data -> bindNowPlayingData(data) }
                    binding.layoutState.root.isVisible = false
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = false
                },
                doOnLoading = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = true
                    binding.layoutState.tvError.isVisible = false
//                    binding.layoutFav.root.isVisible = false
                },
                doOnError = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = true
                    binding.layoutState.tvError.text = it.exception?.message.orEmpty()
                    binding.rvNowPlaying.isVisible = false
                },
                doOnEmpty = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = true
                    binding.layoutState.tvError.text =
                        getString(R.string.text_data_not_available)
                },
            )
        }
    }

    private fun getPopularData() {
        viewModel.getPopular().observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    it.payload?.let { data -> bindPopularData(data) }
                    binding.layoutState.root.isVisible = false
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = false
                },
                doOnLoading = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = true
                    binding.layoutState.tvError.isVisible = false
//                    binding.layoutFav.root.isVisible = false
                },
                doOnError = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = true
                    binding.layoutState.tvError.text = it.exception?.message.orEmpty()
                    binding.rvPopular.isVisible = false
                },
                doOnEmpty = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = true
                    binding.layoutState.tvError.text =
                        getString(R.string.text_data_not_available)
                },
            )
        }
    }

    private fun getUpcomingData() {
        viewModel.getUpcoming().observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    it.payload?.let { data -> bindUpcomingData(data) }
                    binding.layoutState.root.isVisible = false
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = false
                },
                doOnLoading = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = true
                    binding.layoutState.tvError.isVisible = false
//                    binding.layoutFav.root.isVisible = false
                },
                doOnError = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = true
                    binding.layoutState.tvError.text = it.exception?.message.orEmpty()
                    binding.rvUpcoming.isVisible = false
                },
                doOnEmpty = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = true
                    binding.layoutState.tvError.text =
                        getString(R.string.text_data_not_available)
                },
            )
        }
    }

    private fun getTopRatedData() {
        viewModel.getTopRated().observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    it.payload?.let { data -> bindTopRatedData(data) }
                    binding.layoutState.root.isVisible = false
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = false
                },
                doOnLoading = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = true
                    binding.layoutState.tvError.isVisible = false
//                    binding.layoutFav.root.isVisible = false
                },
                doOnError = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = true
                    binding.layoutState.tvError.text = it.exception?.message.orEmpty()
                    binding.rvTopRated.isVisible = false
                },
                doOnEmpty = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = true
                    binding.layoutState.tvError.text =
                        getString(R.string.text_data_not_available)
                },
            )
        }
    }

    private fun setupBannerImgRandom(data: List<BannerImgHome>): BannerImgHome {
        return data.random()
    }

    private fun bindBannerImgRandomData(data: BannerImgHome) {
        binding.layoutBannerHome.ivBannerImgHome.load("https://image.tmdb.org/t/p/w500${data.imgUrl}") {
            crossfade(true)
        }
        binding.layoutBannerHome.tvTitleBanner.text = data.title
        binding.layoutBannerHome.tvDescBanner.text = data.desc
    }

    private fun bindNowPlayingData(data: List<NowPlaying>) {
        nowPlayingAdapter.submitData(data)
    }

    private fun bindPopularData(data: List<Popular>) {
        popularAdapter.submitData(data)
    }

    private fun bindUpcomingData(data: List<Upcoming>) {
        upcomingAdapter.submitData(data)
    }

    private fun bindTopRatedData(data: List<TopRated>) {
        topRatedAdapter.submitData(data)
    }
}
