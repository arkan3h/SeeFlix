package com.arkan.seeflix.presentation.bookmark.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.arkan.seeflix.base.ViewHolderBinder
import com.arkan.seeflix.data.model.Bookmark
import com.arkan.seeflix.databinding.ItemBookmarkBinding

interface BookmarkListener {
    fun onDeleteFavoriteClicked(item: Bookmark)

    fun onItemClicked(movieId: String?)
}

class BookmarkAdapter(
    private val bookmarkListener: BookmarkListener? = null,
) : RecyclerView.Adapter<ViewHolder>() {
    private val dataDiffer =
        AsyncListDiffer(
            this,
            object : DiffUtil.ItemCallback<Bookmark>() {
                override fun areItemsTheSame(
                    oldItem: Bookmark,
                    newItem: Bookmark,
                ): Boolean {
                    return oldItem.movieId == newItem.movieId
                }

                override fun areContentsTheSame(
                    oldItem: Bookmark,
                    newItem: Bookmark,
                ): Boolean {
                    return oldItem.hashCode() == newItem.hashCode()
                }
            },
        )

    fun submitData(data: List<Bookmark>) {
        dataDiffer.submitList(data)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        return BookmarkViewHolder(
            ItemBookmarkBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
            bookmarkListener,
        )
    }

    override fun getItemCount(): Int = dataDiffer.currentList.size

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        (holder as ViewHolderBinder<Bookmark>).bind(dataDiffer.currentList[position])
    }
}

class BookmarkViewHolder(
    private val binding: ItemBookmarkBinding,
    private val bookmarkListener: BookmarkListener?,
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Bookmark> {
    override fun bind(data: Bookmark) {
        setBookmarkData(data)
        setClickListener(data)
    }

    private fun setBookmarkData(data: Bookmark) {
        with(binding) {
            val imgUrl = "https://image.tmdb.org/t/p/w500${data.moviePosterPath}"
            ivMovieImage.load(imgUrl) {
                crossfade(true)
            }
        }
    }

    private fun setClickListener(data: Bookmark) {
        with(binding) {
            itemView.setOnClickListener { bookmarkListener?.onItemClicked(data.movieId) }
        }
    }
}
