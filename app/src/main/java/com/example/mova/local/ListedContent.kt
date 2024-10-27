package com.example.mova.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("listed_content_table")
data class ListedContent(
    @ColumnInfo("listed_content_id")
    val listedContentId: String,
    @PrimaryKey(autoGenerate = false)
    val id: Int = listedContentId.toIntOrNull() ?: -1
)
