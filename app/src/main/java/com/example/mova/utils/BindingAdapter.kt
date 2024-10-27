package com.example.mova.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.mova.utils.Constants.BASE_IMG_URL
import com.example.mova.utils.Constants.DEFAULT_MOVIE_IMG

@BindingAdapter("loadImage")
fun loadImg(imageView: ImageView, url: String?) {
    url?.let {
        imageView.loadImage("$BASE_IMG_URL$url")
    }
        ?: run { imageView.loadImage(DEFAULT_MOVIE_IMG) }
}

@BindingAdapter("IMDB")
fun formatVote(textView: TextView, vote: Number?) {
    vote?.let {
        textView.text = String.format("%.1f", it.toDouble())
    } ?: run { textView.text = "N/A" }
}

@BindingAdapter("setImageResource")
fun setResource(imageView: ImageView, resource: Int) {
    imageView.setImageResource(resource)
}