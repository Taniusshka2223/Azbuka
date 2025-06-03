package com.example.azbuka.ui.screens.profile

import com.example.azbuka.R
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import android.app.Application
import androidx.lifecycle.AndroidViewModel

class ProfileViewModel(private val app: Application) : AndroidViewModel(app) {

    private val firebaseAuth = FirebaseAuth.getInstance()

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _passwordUpdateResult = MutableStateFlow<ResultState>(ResultState.Idle)
    val passwordUpdateResult: StateFlow<ResultState> = _passwordUpdateResult

    init {
        loadUserData()
    }

    private fun loadUserData() {
        _email.value = firebaseAuth.currentUser?.email ?: app.getString(R.string.default_email)
    }

    fun updatePassword(currentPassword: String, newPassword: String) {
        val user = firebaseAuth.currentUser ?: run {
            _passwordUpdateResult.value = ResultState.Error(app.getString(R.string.error_user_not_authenticated))
            return
        }

        val email = user.email ?: run {
            _passwordUpdateResult.value = ResultState.Error(app.getString(R.string.error_email_not_found))
            return
        }

        if (newPassword.length < 6) {
            _passwordUpdateResult.value = ResultState.Error(app.getString(R.string.error_password_length))
            return
        }

        _passwordUpdateResult.value = ResultState.Loading

        viewModelScope.launch {
            val credential = EmailAuthProvider.getCredential(email, currentPassword)
            user.reauthenticate(credential)
                .addOnSuccessListener {
                    user.updatePassword(newPassword)
                        .addOnSuccessListener {
                            _passwordUpdateResult.value = ResultState.Success
                        }
                        .addOnFailureListener {
                            _passwordUpdateResult.value = ResultState.Error(it.message ?: app.getString(R.string.error_password_update))
                        }
                }
                .addOnFailureListener {
                    _passwordUpdateResult.value = ResultState.Error(app.getString(R.string.error_authentication, it.message))
                }
        }
    }

    fun clearResult() {
        _passwordUpdateResult.value = ResultState.Idle
    }

    sealed class ResultState {
        object Idle : ResultState()
        object Loading : ResultState()
        object Success : ResultState()
        data class Error(val message: String) : ResultState()
    }
}
