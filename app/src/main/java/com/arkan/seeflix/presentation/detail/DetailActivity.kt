package com.arkan.seeflix.presentation.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import coil.load
import com.arkan.seeflix.R
import com.arkan.seeflix.data.model.Detail
import com.arkan.seeflix.databinding.ActivityDetailBinding
import com.arkan.seeflix.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailActivity : AppCompatActivity() {
    private val binding: ActivityDetailBinding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }

    private val viewModel: DetailViewModel by viewModel {
        parametersOf(intent.extras)
    }

    companion object {
        const val DETAIL_ID = "DETAIL_ID"

        fun startActivity(
            context: Context,
            id: String,
        ) {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(DETAIL_ID, id)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.detail)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setClickAction()
        viewModel.idMovie?.let { getDetailData(it) }
    }

    private fun setClickAction() {
        binding.ivBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun getDetailData(id: String) {
        viewModel.getDetailData(id).observe(this) { it ->
            it.proceedWhen(
                doOnLoading = {
                    binding.svDetail.isVisible = false
                    binding.ivShare.isVisible = false
                    binding.btnAddBookmark.isVisible = false
                    binding.layoutState.pbLoading.isVisible = true
                    binding.layoutState.tvError.isVisible = false
                },
                doOnSuccess = {
                    binding.svDetail.isVisible = true
                    binding.ivShare.isVisible = true
                    binding.btnAddBookmark.isVisible = true
                    it.payload?.let { data ->
                        checkMovieIsBookmark(data)
                        bindView(data)
                        setClickShare(data)
                    }
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = false
                },
                doOnError = {
                    binding.svDetail.isVisible = false
                    binding.ivShare.isVisible = false
                    binding.btnAddBookmark.isVisible = false
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = true
                    binding.layoutState.tvError.text = it.exception?.message.orEmpty()
                },
            )
        }
    }

    private fun setClickShare(data: Detail) {
        binding.ivShare.setOnClickListener {
            shareMovie(data)
        }
    }

    private fun shareMovie(data: Detail) {
        val shareIntent =
            Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, "Watch this movie! ${data.title}\nhttps://image.tmdb.org/t/p/w500/${data.posterPath}")
            }
        startActivity(
            Intent.createChooser(
                shareIntent,
                "Share Movie",
            ),
        )
    }

    private fun checkMovieIsBookmark(data: Detail) {
        viewModel.checkMovieBookmark(data.id).observe(
            this,
        ) { isFavorite ->
            if (isFavorite.isEmpty()) {
                binding.btnAddBookmark.text = getString(R.string.txt_add_bookmark)
                setClickAddBookmark(data)
                checkMovieIsBookmark(data)
            } else {
                binding.btnAddBookmark.text = getString(R.string.txt_remove_bookmark)
                setClickRemoveBookmark(data.id)
                checkMovieIsBookmark(data)
            }
        }
    }

    private fun setClickAddBookmark(detail: Detail) {
        binding.btnAddBookmark.setOnClickListener {
            addBookmark(detail)
        }
    }

    private fun setClickRemoveBookmark(id: String) {
        binding.btnAddBookmark.setOnClickListener {
            removeBookmark(id)
        }
    }

    private fun addBookmark(detail: Detail) {
        viewModel.addBookmark(detail).observe(this) {
            it.proceedWhen(
                doOnSuccess = {
                    Toast.makeText(
                        this,
                        "Success add bookmark",
                        Toast.LENGTH_SHORT,
                    ).show()
                },
                doOnError = {
                    Toast.makeText(
                        this,
                        "Failed add bookmark",
                        Toast.LENGTH_SHORT,
                    ).show()
                },
            )
        }
    }

    private fun removeBookmark(id: String) {
        viewModel.removeBookmark(id).observe(this) {
            it.proceedWhen(
                doOnSuccess = {
                    Toast.makeText(
                        this,
                        "Success remove bookmark",
                        Toast.LENGTH_SHORT,
                    ).show()
                },
                doOnError = {
                    Toast.makeText(
                        this,
                        "Failed add bookmark",
                        Toast.LENGTH_SHORT,
                    ).show()
                },
            )
        }
    }

    private fun bindView(detail: Detail) {
        detail.let {
            binding.ivBackdrop.load("https://image.tmdb.org/t/p/w500${it.backdropPath}") {
                crossfade(true)
            }
            binding.ivPoster.load("https://image.tmdb.org/t/p/w500${it.posterPath}") {
                crossfade(true)
            }
            binding.tvTitle.text = it.title
            binding.tvDate.text = it.releaseDate
            binding.tvRating.text = it.voteAverage.toString()
            binding.tvOverview.text = it.overview
        }
    }
}
