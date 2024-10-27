package com.example.mova.utils

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.mova.R

fun View.gone() {
    this.visibility = View.GONE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun ImageView.loadImage(url: String) {
    Glide.with(this).load(url).placeholder(R.drawable.placeholder).into(this)
}
