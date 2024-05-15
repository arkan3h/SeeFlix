package com.arkan.seeflix.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.arkan.seeflix.data.model.TopRated
import com.arkan.seeflix.databinding.ItemTopRatedHomeBinding

class TopRatedAdapter(private val itemClick: (TopRated) -> Unit) :
    RecyclerView.Adapter<TopRatedAdapter.ItemTopRatedViewHolder>() {
    private val dataDiffer =
        AsyncListDiffer(
            this,
            object : DiffUtil.ItemCallback<TopRated>() {
                override fun areItemsTheSame(
                    oldItem: TopRated,
                    newItem: TopRated,
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: TopRated,
                    newItem: TopRated,
                ): Boolean {
                    return oldItem.hashCode() == newItem.hashCode()
                }
            },
        )

    fun submitData(data: List<TopRated>) {
        dataDiffer.submitList(data)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ItemTopRatedViewHolder {
        val binding =
            ItemTopRatedHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemTopRatedViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(
        holder: ItemTopRatedViewHolder,
        position: Int,
    ) {
        holder.bindView(dataDiffer.currentList[position])
    }

    override fun getItemCount(): Int = dataDiffer.currentList.size

    class ItemTopRatedViewHolder(
        private val binding: ItemTopRatedHomeBinding,
        val itemClick: (TopRated) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(item: TopRated) {
            with(item) {
                binding.ivTopRatedImg.load("https://image.tmdb.org/t/p/w500${item.imgUrl}") {
                    crossfade(true)
                }
                itemView.setOnClickListener { itemClick(this) }
            }
        }
    }
}
