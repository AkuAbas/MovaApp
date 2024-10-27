package com.example.mova.presentation.ui.profile.myProfile

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
class ProfileViewModel @Inject constructor(
    private val fireStore: FirebaseFirestore,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    val data = MutableLiveData<Account>()
    val isDarkMode = MutableLiveData<Boolean>()

    fun getDataFromFireStore() {
        FirebaseAuth.getInstance().currentUser?.uid?.let { uid ->
            fireStore.collection("account").document(uid).get().addOnSuccessListener { document ->
                if (document != null) {
                    val account = document.toObject(Account::class.java)
                    account?.let {
                        data.value = it
                    }
                } else Log.e("FireStore", "No account data found for user with UID: $uid.")
            }.addOnFailureListener {
                Log.e("FireStore", "Failed to retrieve account data: ${it.localizedMessage}")
            }
        }
    }

    fun setAppTheme(isDark: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean("isDarkMode", isDark)
        editor.apply()
    }

    fun getAppTheme() {
        val isDarkTheme = sharedPreferences.getBoolean("isDarkMode", true)
        isDarkMode.value = isDarkTheme
    }

    fun putSharedPreferences() {
        val editor = sharedPreferences.edit()
        editor.putBoolean("registered", false)
        editor.apply()
    }

}