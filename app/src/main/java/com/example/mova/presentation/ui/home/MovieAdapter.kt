package com.example.mova.presentation.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.mova.databinding.DefaultMovieItemBinding
import com.example.mova.databinding.LargeMovieItemBinding
import com.example.mova.model.Movie

class MovieAdapter(private val isLargeItem: Boolean = false) :
    ListAdapter<Movie, MovieAdapter.MovieViewHolder>(MovieDiffCallback()) {

    lateinit var onClick: (String, Boolean) -> Unit

    private val VIEW_TYPE_DEFAULT = 0
    private val VIEW_TYPE_LARGE = 1

    inner class MovieViewHolder(val movieItemBinding: ViewBinding) :
        RecyclerView.ViewHolder(movieItemBinding.root)

    override fun getItemViewType(position: Int): Int {
        return if (isLargeItem) VIEW_TYPE_LARGE else VIEW_TYPE_DEFAULT
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = if (viewType == VIEW_TYPE_LARGE) {
            LargeMovieItemBinding.inflate(layoutInflater, parent, false)
        } else DefaultMovieItemBinding.inflate(layoutInflater, parent, false)

        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val data = getItem(position)
        when (holder.movieItemBinding) {
            is DefaultMovieItemBinding -> {
                holder.movieItemBinding.movie = data
                holder.movieItemBinding.defaultMovieCard.setOnClickListener {
                    data.id?.let { id ->
                        if (this::onClick.isInitialized) {
                            onClick(id.toString(), data.isMovie)
                        }
                    }
                }
            }

            is LargeMovieItemBinding -> {
                holder.movieItemBinding.movie = data
                holder.movieItemBinding.largeMovieCard.setOnClickListener {
                    data.id?.let { id ->
                        if (this::onClick.isInitialized) {
                            onClick(id.toString(), data.isMovie)
                        }
                    }
                }
            }
        }
    }

    class MovieDiffCallback : ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }
}