package com.arkan.seeflix.presentation.listmovie

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.arkan.seeflix.data.model.Movie
import com.arkan.seeflix.databinding.ActivityListMovieBinding
import com.arkan.seeflix.presentation.listmovie.adapter.ListMovieAdapter
import com.arkan.seeflix.utils.proceedWhen
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
        setContentView(binding.root)
        setupListMovies()
        clickBackButton()
        listMovieViewModel.category?.let { getListMoviesData(it) }
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

    private fun getListMoviesData(category: Int) {
        listMovieViewModel.getListMovies(category).observe(this) {
            it.proceedWhen(
                doOnSuccess = {
                    binding.layoutState.root.isVisible = false
                    binding.layoutState.pbLoading.isVisible = false
                    it.payload?.let { data -> bindListMovies(data) }
                },
                doOnError = {
                    binding.layoutState.root.isVisible = false
                    binding.layoutState.pbLoading.isVisible = false
                    Toast.makeText(this, "Failed Bind List Movies", Toast.LENGTH_SHORT)
                        .show()
                },
                doOnLoading = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = true
                },
            )
        }
    }

    private fun bindListMovies(data: List<Movie>) {
        binding.titleListMovie.text =
            listMovieViewModel.category.let { category ->
                when (category) {
                    1 -> "Now Playing"
                    2 -> "Popular Movies"
                    3 -> "Upcoming Movies"
                    else -> "Top Rated Movies"
                }
            }
        listMovieAdapter.submitData(data)
    }

    private fun navigateToDetail(item: Movie) {
//        DetailMenuActivity.startActivity(requireContext(), item)
    }

    companion object {
        const val CATEGORY_NAME = "CATEGORY_NAME"

        fun startActivity(
            context: Context,
            category: Int,
        ) {
            val intent = Intent(context, ListMovieActivity::class.java)
            intent.putExtra(CATEGORY_NAME, category)
            context.startActivity(intent)
        }
    }
}
