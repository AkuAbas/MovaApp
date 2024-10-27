package com.example.mova.presentation.ui.auth

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mova.api.NetworkResponse
import com.example.mova.di.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    val uiState = MutableLiveData<RegisterUiState>()

    fun registerUser(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            viewModelScope.launch {
                repository.register(email, password).collectLatest {
                    when (it) {
                        is NetworkResponse.Loading -> uiState.value = RegisterUiState.Loading
                        is NetworkResponse.Error -> it.message?.let {
                            uiState.value = RegisterUiState.Error(it)
                        }

                        is NetworkResponse.Success -> it.data?.user?.email?.let {
                            uiState.value = RegisterUiState.Success(it)
                            saveEmailToPreferences(it)
                        }


                    }
                }
            }
        } else uiState.value = RegisterUiState.EmptyFields

    }

    fun putSharedPref() {
        val editor = sharedPreferences.edit()
        editor.putBoolean("registered", true)
        editor.apply()
    }

    private fun saveEmailToPreferences(email: String) {
        val editor = sharedPreferences.edit()
        editor.putString("user_email", email)
        editor.apply()
    }


    sealed class RegisterUiState {
        data object Loading : RegisterUiState()
        data class Success(val email: String) : RegisterUiState()
        data class Error(val message: String) : RegisterUiState()
        data object EmptyFields : RegisterUiState()
    }
}


