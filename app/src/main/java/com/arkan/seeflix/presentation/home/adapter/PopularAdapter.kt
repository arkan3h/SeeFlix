package com.arkan.seeflix.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.arkan.seeflix.data.model.Popular
import com.arkan.seeflix.databinding.ItemPopularHomeBinding

class PopularAdapter(private val itemClick: (Popular) -> Unit) :
    RecyclerView.Adapter<PopularAdapter.ItemPopularViewHolder>() {
    private val dataDiffer =
        AsyncListDiffer(
            this,
            object : DiffUtil.ItemCallback<Popular>() {
                override fun areItemsTheSame(
                    oldItem: Popular,
                    newItem: Popular,
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: Popular,
                    newItem: Popular,
                ): Boolean {
                    return oldItem.hashCode() == newItem.hashCode()
                }
            },
        )

    fun submitData(data: List<Popular>) {
        dataDiffer.submitList(data)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ItemPopularViewHolder {
        val binding =
            ItemPopularHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemPopularViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(
        holder: ItemPopularViewHolder,
        position: Int,
    ) {
        holder.bindView(dataDiffer.currentList[position])
    }

    override fun getItemCount(): Int = dataDiffer.currentList.size

    class ItemPopularViewHolder(
        private val binding: ItemPopularHomeBinding,
        val itemClick: (Popular) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(item: Popular) {
            with(item) {
                binding.ivPopularImg.load("https://image.tmdb.org/t/p/w500${item.imgUrl}") {
                    crossfade(true)
                }
                itemView.setOnClickListener { itemClick(this) }
            }
        }
    }
}
