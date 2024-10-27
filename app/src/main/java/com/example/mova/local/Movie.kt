package com.example.mova.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("movie_table")
data class Movie(
    @ColumnInfo("content_id")
    val contentId: String,
    @ColumnInfo("movie_poster")
    val poster: String,
    @ColumnInfo("movie_rating")
    val rating: Double,
    @ColumnInfo("movie_title")
    val title: String,
    @PrimaryKey(autoGenerate = false)
    val id: Int = contentId.toInt()
)
