package com.arkan.seeflix.presentation.listmovie.adapter

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.arkan.seeflix.data.model.Movie
import com.arkan.seeflix.databinding.ItemMovieBinding

class ListMovieViewHolder(
    private val binding: ItemMovieBinding,
    val itemClick: (Movie) -> Unit,
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Movie) {
        with(item) {
            binding.ivMovieImage.load("https://image.tmdb.org/t/p/w500${item.imgUrl}") {
                crossfade(true)
            }
            itemView.setOnClickListener { itemClick(this) }
        }
    }
}
