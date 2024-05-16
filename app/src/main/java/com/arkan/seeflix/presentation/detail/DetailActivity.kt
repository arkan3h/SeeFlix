package com.arkan.seeflix.presentation.detail

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.arkan.seeflix.data.model.MovieDetail
import com.arkan.seeflix.data.repository.movie.MovieRepository
import com.arkan.seeflix.databinding.ActivityDetailBinding
import com.arkan.seeflix.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailActivity : AppCompatActivity() {
    private val binding: ActivityDetailBinding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }

    private val viewModel: ViewModelDetailActivity by viewModel {
        parametersOf(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel.id?.let { setupDetailData(it) }
        setClickAction()
    }

    private fun setClickAction() {
        binding.ivBack.setOnClickListener {
            finish()
        }
        // Button action here
        binding.btnShare.setOnClickListener {

        }
        binding.btnAddBookmark.setOnClickListener {

        }
    }

    private fun openLink() {
        val url = viewModel.link
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    private fun setupDetailData(id: String) {
        viewModel.getDetail(id).observe(this) {
            it.proceedWhen(
                doOnLoading = {
                    // Loading state

                },
                doOnSuccess = {
                    // Success state
                },
                doOnError = {
                    // Error state
                },
            )
        }
    }

    private fun bindDetailData(movieDetail: MovieDetail?) {
        movieDetail?.let { detail ->
            binding.ivPoster.load(detail.posterPath) {
                // Coil load options if needed
            }
            // Bind other details
        }
    }

    private fun openLinkImg() {
        val url = viewModel.linkImg
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    companion object {
        const val ID_Movie = "ID_Movie"

        fun startActivity(
            context: Context,
            id: String,
            movieRepository: MovieRepository,
        ) {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(ID_Movie, id)
            context.startActivity(intent)
        }
    }
}
