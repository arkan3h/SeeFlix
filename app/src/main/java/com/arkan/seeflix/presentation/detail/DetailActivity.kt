package com.arkan.seeflix.presentation.detail

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.arkan.seeflix.databinding.ActivityDetailBinding
import org.koin.android.ext.android.inject

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val viewModel: ViewModelDetailActivity by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getStringExtra(ID_MOVIE)
        id?.let { viewModel.getDetail(it) }

        setClickAction()
    }

    private fun setClickAction() {
        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.btnShare.setOnClickListener {
            val url = viewModel.link
            openLink(url)
        }

        binding.ivPoster.setOnClickListener {
            val url = viewModel.linkImg
            openLink(url)
        }

        binding.btnAddBookmark.setOnClickListener {
            // Add bookmark
        }
    }

    private fun openLink(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    companion object {
        const val ID_MOVIE = "ID_Movie"
    }
}
