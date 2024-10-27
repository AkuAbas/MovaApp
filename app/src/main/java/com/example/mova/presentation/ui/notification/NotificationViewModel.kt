package com.example.mova.presentation.ui.notification

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mova.di.MovaRepository
import com.example.mova.model.Notification
import com.google.firebase.firestore.FirebaseFirestoreException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(private val repository: MovaRepository) :
    ViewModel() {


    val uiState = MutableLiveData<NotificationUiState>()

    fun getNotifications() {
        viewModelScope.launch {
            uiState.value = NotificationUiState.Loading
            try {
                val result = repository.getNotifications()
                val list = result.documents.mapNotNull { it.toObject(Notification::class.java) }
                uiState.value = NotificationUiState.Success(list)
            } catch (e: FirebaseFirestoreException) {
                uiState.value = NotificationUiState.Error("FireStore error: ${e.localizedMessage}")
            } catch (e: Exception) {
                uiState.value = NotificationUiState.Error("Error: ${e.localizedMessage}")
            }
        }
    }

    sealed class NotificationUiState {
        data object Loading : NotificationUiState()
        class Error(val message: String = "") : NotificationUiState()
        class Success(val notificationList: List<Notification>) : NotificationUiState()
    }
}