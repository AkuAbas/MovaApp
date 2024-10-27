package com.example.mova.presentation.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mova.databinding.ListItemBinding
import com.example.mova.local.Movie

class MyListAdapter : ListAdapter<Movie, MyListAdapter.ContentViewHolder>(ContentDiffCallback()) {

    lateinit var onClick: (Movie) -> Unit

    inner class ContentViewHolder(val listItemBinding: ListItemBinding) :
        RecyclerView.ViewHolder(listItemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
        val view = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContentViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
        val data = getItem(position)
        holder.listItemBinding.content = data
        holder.listItemBinding.listContentCard.setOnClickListener {
            onClick(data)
        }
    }

    class ContentDiffCallback : ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

    }
}