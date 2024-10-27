package com.example.mova.model

import com.google.firebase.firestore.PropertyName

data class Notification(
    @PropertyName("Title")
    val title: String = "",
    @PropertyName("Date")
    val date: String = "",
    @PropertyName("Episode")
    val episode: String = "",
    @PropertyName("Image")
    val image: String = ""
)
