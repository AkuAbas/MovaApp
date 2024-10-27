package com.example.mova.model

import android.net.Uri
import com.google.firebase.firestore.PropertyName

data class Account(
    @PropertyName("fullName")
    val fullName: String? = null,
    @PropertyName("nickName")
    val nickName: String? = null,
    @PropertyName("email")
    val email: String? = null,
    @PropertyName("country")
    val country: String? = null,
    @PropertyName("gender")
    val gender: String? = null,
    @PropertyName("image")
    val image: String? = null
)
