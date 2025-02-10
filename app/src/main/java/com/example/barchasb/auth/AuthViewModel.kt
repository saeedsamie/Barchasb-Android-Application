package com.example.barchasb.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.barchasb.api.ApiClient
import com.example.barchasb.api.User
import com.example.barchasb.api.UserApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val userApi = ApiClient.create<UserApi>()

    // State management for login and logout
    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> get() = _loginState

    private val _logoutState = MutableStateFlow<LogoutState>(LogoutState.Idle)
    val logoutState: StateFlow<LogoutState> get() = _logoutState

    fun login(username: String, password: String) {
        _loginState.value = LoginState.Loading
        viewModelScope.launch {
            try {
                val response = userApi.login(User(username, password))
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

    private val _signupState = MutableStateFlow<SignupState>(SignupState.Idle)
    val signupState: StateFlow<SignupState> get() = _signupState

    fun signup(
        username: String,
        password: String,
        phone: String,
        age: String,
        languages: String,
        education: String
    ) {
        _signupState.value = SignupState.Loading
        viewModelScope.launch {
            try {
                val response = userApi.signup(User(username, password))
                if (response.isSuccessful) {
                    _signupState.value =
                        SignupState.Success(response.body()?.get("message") ?: "Signup successful")
                } else {
                    _signupState.value =
                        SignupState.Error("Signup failed: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                _signupState.value = SignupState.Error("Network error: ${e.message}")
            }
        }
    }
}


// State representation for signup
sealed class SignupState {
    object Idle : SignupState()
    object Loading : SignupState()
    data class Success(val message: String) : SignupState()
    data class Error(val message: String) : SignupState()
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

