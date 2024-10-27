package com.example.mova.presentation.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mova.databinding.ActorItemBinding
import com.example.mova.model.Cast

class ActorsAdapter : ListAdapter<Cast, ActorsAdapter.ActorViewHolder>(ActorDiffCallback()) {

    inner class ActorViewHolder(val actorItemBinding: ActorItemBinding) :
        RecyclerView.ViewHolder(actorItemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder {
        val view = ActorItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ActorViewHolder(view)
    }

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
        val data = getItem(position)
        holder.actorItemBinding.actor = data
    }

    class ActorDiffCallback : ItemCallback<Cast>() {
        override fun areItemsTheSame(oldItem: Cast, newItem: Cast): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Cast, newItem: Cast): Boolean {
            return oldItem == newItem
        }

    }
}