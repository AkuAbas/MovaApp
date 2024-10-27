package com.example.mova.presentation.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mova.databinding.ImageItemBinding
import com.example.mova.model.Movie

class HomePagerAdapter :
    ListAdapter<Movie, HomePagerAdapter.ImageViewHolder>(ImageDiffCallback()) {

    lateinit var onClick: () -> Unit

    inner class ImageViewHolder(val imageItemBinding: ImageItemBinding) :
        RecyclerView.ViewHolder(imageItemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = ImageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val data = getItem(position)
        holder.imageItemBinding.movie = data
        holder.imageItemBinding.imageViewNotification.setOnClickListener {
            onClick()
        }
    }

    class ImageDiffCallback : ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

    }
}