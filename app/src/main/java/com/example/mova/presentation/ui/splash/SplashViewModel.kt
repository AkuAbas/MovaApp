package com.example.mova.presentation.ui.splash

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val sharedPreferences: SharedPreferences) :
    ViewModel() {

    val isDarkMode = MutableLiveData<Boolean>()

    fun getAppTheme() {
        val isDarkTheme = sharedPreferences.getBoolean("isDarkMode", true)
        isDarkMode.value = isDarkTheme
    }


}