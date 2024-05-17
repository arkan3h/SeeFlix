package com.arkan.seeflix.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import coil.load
import com.arkan.seeflix.R
import com.arkan.seeflix.data.model.BannerImgHome
import com.arkan.seeflix.data.model.Movie
import com.arkan.seeflix.databinding.FragmentHomeBinding
import com.arkan.seeflix.presentation.detail.DetailActivity
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
            DetailActivity.startActivity(requireContext(), it.id)
        }
    }

    private val popularAdapter: MovieAdapter by lazy {
        MovieAdapter {
            DetailActivity.startActivity(requireContext(), it.id)
        }
    }

    private val upcomingAdapter: MovieAdapter by lazy {
        MovieAdapter {
            DetailActivity.startActivity(requireContext(), it.id)
        }
    }

    private val topRatedAdapter: MovieAdapter by lazy {
        MovieAdapter {
            DetailActivity.startActivity(requireContext(), it.id)
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
        getBannerHome()
        getNowPlayingData()
        getPopularData()
        getUpcomingData()
        getTopRatedData()
    }

    private fun setClickAction() {
        binding.layoutBannerHome.ibInfoBanner.setOnClickListener {
            randomMovie?.let { data ->
                DetailActivity.startActivity(requireContext(), data.id)
            }
        }

        binding.layoutBannerHome.ibShareBanner.setOnClickListener {
            randomMovie?.let { data -> shareMovie(data) }
        }
    }

    private fun navigateToListMovie() {
        binding.layoutNowPlaying.ivListGridListMovie.setOnClickListener {
            ListMovieActivity.startActivity(requireContext(), 1)
        }

        binding.layoutPopular.ivListGridListMovie.setOnClickListener {
            ListMovieActivity.startActivity(requireContext(), 2)
        }

        binding.layoutUpcoming.ivListGridListMovie.setOnClickListener {
            ListMovieActivity.startActivity(requireContext(), 3)
        }

        binding.layoutTopRated.ivListGridListMovie.setOnClickListener {
            ListMovieActivity.startActivity(requireContext(), 4)
        }
    }

    private fun setupListData() {
        binding.layoutNowPlaying.rvListMovie.apply {
            adapter = nowPlayingAdapter
        }
        binding.layoutPopular.rvListMovie.apply {
            adapter = popularAdapter
        }
        binding.layoutUpcoming.rvListMovie.apply {
            adapter = upcomingAdapter
        }
        binding.layoutTopRated.rvListMovie.apply {
            adapter = topRatedAdapter
        }
    }

    private fun getBannerHome() {
        viewModel.getBannerImgHome().observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    it.payload?.let { data ->
                        randomMovie = setupBannerImgRandom(data)
                        bindBannerHome(randomMovie!!)
                    }
                    binding.layoutState.root.isVisible = false
                    binding.layoutState.tvError.isVisible = false
                    binding.bannerShimmer.isVisible = false
                },
                doOnLoading = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.tvError.isVisible = false
                    binding.bannerShimmer.isVisible = true
                    binding.shimmerFrameLayoutBanner.startShimmer()
                },
                doOnError = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.tvError.isVisible = true
                    binding.layoutState.tvError.text = it.exception?.message.orEmpty()
                    binding.bannerShimmer.isVisible = false
                },
                doOnEmpty = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.tvError.isVisible = true
                    binding.layoutState.tvError.text =
                        getString(R.string.text_data_not_available)
                    binding.bannerShimmer.isVisible = false
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
                    binding.layoutState.tvError.isVisible = false
                    binding.layoutNowPlaying.movieShimmer.isVisible = false
                },
                doOnLoading = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.tvError.isVisible = false
                    binding.layoutNowPlaying.movieShimmer.isVisible = true
                    binding.layoutNowPlaying.shimmerFrameLayout.startShimmer()
                },
                doOnError = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.tvError.isVisible = true
                    binding.layoutState.tvError.text = it.exception?.message.orEmpty()
                    binding.layoutNowPlaying.rvListMovie.isVisible = false
                    binding.layoutNowPlaying.movieShimmer.isVisible = false
                },
                doOnEmpty = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.tvError.isVisible = true
                    binding.layoutState.tvError.text =
                        getString(R.string.text_data_not_available)
                    binding.layoutNowPlaying.movieShimmer.isVisible = false
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
                    binding.layoutState.tvError.isVisible = false
                    binding.layoutPopular.movieShimmer.isVisible = false
                },
                doOnLoading = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.tvError.isVisible = false
                    binding.layoutPopular.movieShimmer.isVisible = true
                    binding.layoutPopular.shimmerFrameLayout.startShimmer()
                },
                doOnError = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.tvError.isVisible = true
                    binding.layoutState.tvError.text = it.exception?.message.orEmpty()
                    binding.layoutPopular.rvListMovie.isVisible = false
                    binding.layoutPopular.movieShimmer.isVisible = false
                },
                doOnEmpty = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.tvError.isVisible = true
                    binding.layoutState.tvError.text =
                        getString(R.string.text_data_not_available)
                    binding.layoutPopular.movieShimmer.isVisible = false
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
                    binding.layoutState.tvError.isVisible = false
                    binding.layoutUpcoming.movieShimmer.isVisible = false
                },
                doOnLoading = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.tvError.isVisible = false
                    binding.layoutUpcoming.movieShimmer.isVisible = true
                    binding.layoutUpcoming.shimmerFrameLayout.startShimmer()
                },
                doOnError = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.tvError.isVisible = true
                    binding.layoutState.tvError.text = it.exception?.message.orEmpty()
                    binding.layoutUpcoming.rvListMovie.isVisible = false
                    binding.layoutUpcoming.movieShimmer.isVisible = false
                },
                doOnEmpty = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.tvError.isVisible = true
                    binding.layoutState.tvError.text =
                        getString(R.string.text_data_not_available)
                    binding.layoutUpcoming.movieShimmer.isVisible = false
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
                    binding.layoutState.tvError.isVisible = false
                    binding.layoutTopRated.movieShimmer.isVisible = false
                },
                doOnLoading = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.tvError.isVisible = false
                    binding.layoutTopRated.movieShimmer.isVisible = true
                    binding.layoutTopRated.shimmerFrameLayout.startShimmer()
                },
                doOnError = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.tvError.isVisible = true
                    binding.layoutState.tvError.text = it.exception?.message.orEmpty()
                    binding.layoutTopRated.rvListMovie.isVisible = false
                    binding.layoutTopRated.movieShimmer.isVisible = false
                },
                doOnEmpty = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.tvError.isVisible = true
                    binding.layoutState.tvError.text =
                        getString(R.string.text_data_not_available)
                    binding.layoutTopRated.movieShimmer.isVisible = false
                },
            )
        }
    }

    private fun shareMovie(movie: BannerImgHome) {
        val shareIntent =
            Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(
                    Intent.EXTRA_TEXT,
                    "Watch this movie! ${movie.title}\nhttps://image.tmdb.org/t/p/w500/${movie.imgUrl}",
                )
            }
        startActivity(
            Intent.createChooser(
                shareIntent,
                "Share Movie",
            ),
        )
    }

    private fun setupBannerImgRandom(data: List<BannerImgHome>): BannerImgHome {
        return data.random()
    }

    private fun bindBannerHome(data: BannerImgHome) {
        binding.layoutBannerHome.ivBannerImgHome.load("https://image.tmdb.org/t/p/w500${data.imgUrl}") {
            crossfade(true)
        }
        binding.layoutBannerHome.tvTitleBanner.text = data.title
        binding.layoutBannerHome.tvDescBanner.text = data.desc
    }

    private fun bindNowPlayingData(data: List<Movie>) {
        binding.layoutNowPlaying.tvTitleListMovie.text = getString(R.string.text_now_playing_home)
        nowPlayingAdapter.submitData(data)
    }

    private fun bindPopularData(data: List<Movie>) {
        binding.layoutPopular.tvTitleListMovie.text = getString(R.string.text_popular_home)
        popularAdapter.submitData(data)
    }

    private fun bindUpcomingData(data: List<Movie>) {
        binding.layoutUpcoming.tvTitleListMovie.text = getString(R.string.text_upcoming_home)
        upcomingAdapter.submitData(data)
    }

    private fun bindTopRatedData(data: List<Movie>) {
        binding.layoutTopRated.tvTitleListMovie.text = getString(R.string.text_top_rated_home)
        topRatedAdapter.submitData(data)
    }
}
