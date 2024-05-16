package com.arkan.seeflix.presentation.listmovie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.arkan.seeflix.data.model.Movie
import com.arkan.seeflix.databinding.ItemMovieBinding

class ListMovieAdapter(private val itemClick: (Movie) -> Unit) :
    PagingDataAdapter<Movie, ListMovieViewHolder>(MovieDiffCallBack()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ListMovieViewHolder {
        return ListMovieViewHolder(
            ItemMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
            itemClick,
        )
    }

    override fun onBindViewHolder(
        holder: ListMovieViewHolder,
        position: Int,
    ) {
        getItem(position)?.let { holder.bind(it) }
    }
}

class MovieDiffCallBack : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(
        oldItem: Movie,
        newItem: Movie,
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: Movie,
        newItem: Movie,
    ): Boolean {
        return oldItem == newItem
    }
}
