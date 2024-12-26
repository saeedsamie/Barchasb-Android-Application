package com.example.barchasb.auth

import TokenManager
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.barchasb.api.ApiClient
import com.example.barchasb.api.AuthApi
import com.example.barchasb.api.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val authApi = ApiClient.create<AuthApi>()

    // State management for login and logout
    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> get() = _loginState

    private val _logoutState = MutableStateFlow<LogoutState>(LogoutState.Idle)
    val logoutState: StateFlow<LogoutState> get() = _logoutState

    fun login(username: String, password: String) {
        _loginState.value = LoginState.Loading
        viewModelScope.launch {
            try {
                val response = authApi.login(User(username, password))
                if (response.isSuccessful) {
                    val tokenResponse = response.body()
                    val apiToken = tokenResponse?.access_token.orEmpty()
                    println("LOGIN API TOKEN $apiToken")

                    _loginState.value = LoginState.Success(apiToken)
                } else {
                    _loginState.value =
                        LoginState.Error("Login failed: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                _loginState.value = LoginState.Error("Network error: ${e.message}")
            }
        }
    }

    fun logout(apiToken: String) {
        _logoutState.value = LogoutState.Loading
        viewModelScope.launch {
            try {
                val response = authApi.logout("Bearer $apiToken")
                println("LOGOUT API TOKEN $apiToken")
                if (response.isSuccessful) {
                    _logoutState.value = LogoutState.Success
                } else {
                    _logoutState.value =
                        LogoutState.Error("Logout failed: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                _logoutState.value = LogoutState.Error("Network error: ${e.message}")
            }
        }
    }

    fun saveToken(context: Context, token: String) {
        TokenManager.saveToken(context, token)
    }

    fun getToken(context: Context): String? {
        return TokenManager.getToken(context)
    }

    fun clearToken(context: Context) {
        TokenManager.clearToken(context)
    }

}

// State representation for login
sealed class LoginState {
    data object Idle : LoginState()
    data object Loading : LoginState()
    data class Success(val token: String) : LoginState()
    data class Error(val message: String) : LoginState()
}

// State representation for logout
sealed class LogoutState {
    data object Idle : LogoutState()
    data object Loading : LogoutState()
    data object Success : LogoutState()
    data class Error(val message: String) : LogoutState()
}

