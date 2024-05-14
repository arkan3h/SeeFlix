package com.arkan.seeflix.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.arkan.seeflix.data.model.NowPlaying
import com.arkan.seeflix.databinding.ItemNowPlayingHomeBinding

class NowPlayingAdapter(private val itemClick: (NowPlaying) -> Unit) :
    RecyclerView.Adapter<NowPlayingAdapter.ItemNowPlayingViewHolder>() {
    private val dataDiffer =
        AsyncListDiffer(
            this,
            object : DiffUtil.ItemCallback<NowPlaying>() {
                override fun areItemsTheSame(
                    oldItem: NowPlaying,
                    newItem: NowPlaying,
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: NowPlaying,
                    newItem: NowPlaying,
                ): Boolean {
                    return oldItem.hashCode() == newItem.hashCode()
                }
            },
        )

    fun submitData(data: List<NowPlaying>) {
        dataDiffer.submitList(data)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ItemNowPlayingViewHolder {
        val binding =
            ItemNowPlayingHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemNowPlayingViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(
        holder: ItemNowPlayingViewHolder,
        position: Int,
    ) {
        holder.bindView(dataDiffer.currentList[position])
    }

    override fun getItemCount(): Int = dataDiffer.currentList.size

    class ItemNowPlayingViewHolder(
        private val binding: ItemNowPlayingHomeBinding,
        val itemClick: (NowPlaying) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(item: NowPlaying) {
            with(item) {
                binding.ivNowPlayingImg.load("https://image.tmdb.org/t/p/w500${item.imgUrl}") {
                    crossfade(true)
                }
                itemView.setOnClickListener { itemClick(this) }
            }
        }
    }
}
