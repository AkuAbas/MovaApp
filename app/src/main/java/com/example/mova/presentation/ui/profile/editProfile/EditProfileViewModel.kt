package com.example.mova.presentation.ui.profile.editProfile

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mova.model.Account
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val fireStore: FirebaseFirestore,
    private val sharedPreferences: SharedPreferences
) :
    ViewModel() {

    val email = MutableLiveData<String>()

    fun getEmailFromPreferences() {
        email.value = sharedPreferences.getString("user_email", "email not found.")
    }

    fun updateFireStore(account: Account) {
        FirebaseAuth.getInstance().currentUser?.uid?.let { uid ->
            fireStore.collection("account").document(uid).set(account).addOnSuccessListener {

            }.addOnFailureListener {
                it.localizedMessage?.let { errorMsg -> Log.e("FireStore", errorMsg) }
            }
        }
    }


}