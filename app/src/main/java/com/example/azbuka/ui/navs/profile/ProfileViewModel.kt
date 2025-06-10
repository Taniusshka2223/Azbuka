package com.example.azbuka.ui.navs.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.azbuka.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ProfileViewModel(private val app: Application) : AndroidViewModel(app) {

    private val firebaseAuth = FirebaseAuth.getInstance()

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    init {
        loadUserData()
    }

    private fun loadUserData() {
        _email.value = firebaseAuth.currentUser?.email ?: app.getString(R.string.default_email)
    }
}
