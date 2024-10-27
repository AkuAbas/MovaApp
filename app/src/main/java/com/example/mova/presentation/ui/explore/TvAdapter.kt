package com.example.mova.presentation.ui.explore

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mova.databinding.TvItemBinding
import com.example.mova.model.TvSeries

class TvAdapter : ListAdapter<TvSeries, TvAdapter.TvViewHolder>(TvDiffCallback()) {

    lateinit var onClick: (String, Boolean) -> Unit

    inner class TvViewHolder(val tvItemBinding: TvItemBinding) :
        RecyclerView.ViewHolder(tvItemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvViewHolder {
        val view = TvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvViewHolder(view)
    }

    override fun onBindViewHolder(holder: TvViewHolder, position: Int) {
        val data = getItem(position)
        holder.tvItemBinding.tvShow = data
        holder.tvItemBinding.largeTvCard.setOnClickListener {
            data.id?.let {
                onClick(it.toString(), data.isMovie)
            }
        }
    }

    class TvDiffCallback : ItemCallback<TvSeries>() {
        override fun areItemsTheSame(oldItem: TvSeries, newItem: TvSeries): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TvSeries, newItem: TvSeries): Boolean {
            return oldItem == newItem
        }

    }
}