package com.arkan.seeflix.presentation.listmovie

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.arkan.seeflix.R
import com.arkan.seeflix.data.model.Movie
import com.arkan.seeflix.databinding.ActivityListMovieBinding
import com.arkan.seeflix.presentation.detail.DetailActivity
import com.arkan.seeflix.presentation.listmovie.adapter.ListMovieAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ListMovieActivity : AppCompatActivity() {
    private val binding: ActivityListMovieBinding by lazy {
        ActivityListMovieBinding.inflate(layoutInflater)
    }

    private val listMovieViewModel: ListMovieViewModel by viewModel {
        parametersOf(intent.extras)
    }

    private val listMovieAdapter: ListMovieAdapter by lazy {
        ListMovieAdapter {
            navigateToDetail(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.list_movie)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupListMovies()
        clickBackButton()
        bindListMovies()
    }

    private fun clickBackButton() {
        binding.ivArrowBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setupListMovies() {
        binding.rvMovieList.apply {
            adapter = listMovieAdapter
        }
    }

    private fun bindListMovies() {
        listMovieViewModel.category.let { category ->
            binding.titleListMovie.text =
                when (category) {
                    1 -> "Now Playing Movies"
                    2 -> "Popular Movies"
                    3 -> "Upcoming Movies"
                    else -> "Top Rated Movies"
                }

            when (category) {
                1 -> getListNowPlaying()
                2 -> getListPopular()
                3 -> getListUpcoming()
                else -> getListTopRated()
            }
        }
    }

    private fun getListTopRated() {
        lifecycleScope.launch {
            listMovieViewModel.getListTopRated().collectLatest { pagingData ->
                listMovieAdapter.submitData(pagingData)
            }
        }
    }

    private fun getListPopular() {
        lifecycleScope.launch {
            listMovieViewModel.getListPopular().collectLatest { pagingData ->
                listMovieAdapter.submitData(pagingData)
            }
        }
    }

    private fun getListNowPlaying() {
        lifecycleScope.launch {
            listMovieViewModel.getListNowPlaying().collectLatest { pagingData ->
                listMovieAdapter.submitData(pagingData)
            }
        }
    }

    private fun getListUpcoming() {
        lifecycleScope.launch {
            listMovieViewModel.getListUpcoming().collectLatest { pagingData ->
                listMovieAdapter.submitData(pagingData)
            }
        }
    }

    private fun navigateToDetail(item: Movie) {
        DetailActivity.startActivity(this, item.id)
    }

    companion object {
        const val CATEGORY = "CATEGORY"

        fun startActivity(
            context: Context,
            category: Int,
        ) {
            val intent = Intent(context, ListMovieActivity::class.java)
            intent.putExtra(CATEGORY, category)
            context.startActivity(intent)
        }
    }
}
