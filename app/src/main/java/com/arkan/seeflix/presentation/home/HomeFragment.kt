package com.arkan.seeflix.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import coil.load
import com.arkan.seeflix.R
import com.arkan.seeflix.data.model.BannerImgHome
import com.arkan.seeflix.data.model.Movie
import com.arkan.seeflix.databinding.FragmentHomeBinding
import com.arkan.seeflix.presentation.home.adapter.MovieAdapter
import com.arkan.seeflix.presentation.listmovie.ListMovieActivity
import com.arkan.seeflix.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private var randomMovie: BannerImgHome? = null
    private val viewModel: HomeViewModel by viewModel()

    private val nowPlayingAdapter: MovieAdapter by lazy {
        MovieAdapter {
//            DetailActivity.startActivity(requireContext(), it.id)
        }
    }

    private val popularAdapter: MovieAdapter by lazy {
        MovieAdapter {
//            DetailActivity.startActivity(requireContext(), it.id)
        }
    }

    private val upcomingAdapter: MovieAdapter by lazy {
        MovieAdapter {
//            DetailActivity.startActivity(requireContext(), it.id)
        }
    }

    private val topRatedAdapter: MovieAdapter by lazy {
        MovieAdapter {
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
        navigateToListMovie()
        setupListData()
        getBannerImgRandom()
        getNowPlayingData()
        getPopularData()
        getUpcomingData()
        getTopRatedData()
        setClickAction()
    }

    private fun setClickAction() {
        binding.layoutBannerHome.ibInfoBanner.setOnClickListener {
            Toast.makeText(requireContext(), "Info Ditekan", Toast.LENGTH_SHORT).show()
        }

        binding.layoutBannerHome.ibShareBanner.setOnClickListener {
            randomMovie?.let { data -> shareMovie(data) }
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
                        randomMovie = setupBannerImgRandom(data)
                        bindBannerImgRandomData(randomMovie!!)
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

    private fun shareMovie(movie: BannerImgHome) {
        val shareIntent =
            Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, "Watch this movie! ${movie.title}\nhttps://image.tmdb.org/t/p/w500/${movie.imgUrl}")
            }
        startActivity(Intent.createChooser(
            shareIntent,
            "Share Movie"))
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

    private fun bindNowPlayingData(data: List<Movie>) {
        nowPlayingAdapter.submitData(data)
    }

    private fun bindPopularData(data: List<Movie>) {
        popularAdapter.submitData(data)
    }

    private fun bindUpcomingData(data: List<Movie>) {
        upcomingAdapter.submitData(data)
    }

    private fun bindTopRatedData(data: List<Movie>) {
        topRatedAdapter.submitData(data)
    }
}
