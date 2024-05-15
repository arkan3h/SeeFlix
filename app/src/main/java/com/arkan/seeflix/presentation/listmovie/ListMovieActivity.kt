package com.arkan.seeflix.presentation.listmovie

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.arkan.seeflix.data.model.Movie
import com.arkan.seeflix.databinding.ActivityListMovieBinding
import com.arkan.seeflix.presentation.listmovie.adapter.ListMovieAdapter
import com.arkan.seeflix.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListMovieActivity : AppCompatActivity() {
    private val binding: ActivityListMovieBinding by lazy {
        ActivityListMovieBinding.inflate(layoutInflater)
    }

    private val listMovieViewModel: ListMovieViewModel by viewModel()

    private val listMovieAdapter: ListMovieAdapter by lazy {
        ListMovieAdapter {
            navigateToDetail(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupListMovies()
        getListMoviesData(1)
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
        listMovieAdapter.submitData(data)
    }

    private fun navigateToDetail(item: Movie) {
//        DetailMenuActivity.startActivity(requireContext(), item)
    }
}
