package com.arkan.seeflix.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.arkan.seeflix.data.model.Movie
import com.arkan.seeflix.databinding.ItemUpcomingHomeBinding

class UpcomingAdapter(private val itemClick: (Movie) -> Unit) :
    RecyclerView.Adapter<UpcomingAdapter.ItemUpcomingViewHolder>() {
    private val dataDiffer =
        AsyncListDiffer(
            this,
            object : DiffUtil.ItemCallback<Movie>() {
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
                    return oldItem.hashCode() == newItem.hashCode()
                }
            },
        )

    fun submitData(data: List<Movie>) {
        dataDiffer.submitList(data)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ItemUpcomingViewHolder {
        val binding =
            ItemUpcomingHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemUpcomingViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(
        holder: ItemUpcomingViewHolder,
        position: Int,
    ) {
        holder.bindView(dataDiffer.currentList[position])
    }

    override fun getItemCount(): Int = dataDiffer.currentList.size

    class ItemUpcomingViewHolder(
        private val binding: ItemUpcomingHomeBinding,
        val itemClick: (Movie) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(item: Movie) {
            with(item) {
                binding.ivUpcomingImg.load("https://image.tmdb.org/t/p/w500${item.imgUrl}") {
                    crossfade(true)
                }
                itemView.setOnClickListener { itemClick(this) }
            }
        }
    }
}
