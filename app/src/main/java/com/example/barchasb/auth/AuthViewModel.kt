package com.example.barchasb.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.barchasb.api.ApiClient
import com.example.barchasb.api.AuthApi
import com.example.barchasb.api.User
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val authApi = ApiClient.create<AuthApi>()

    fun login(username: String, password: String) {
        viewModelScope.launch {
            try {
                val response = authApi.login(User(username, password))
                if (response.isSuccessful) {
                    val token = response.body()?.token
                    println("Login successful. Token: $token")
                } else {
                    println("Login failed: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                println("Network error: ${e.message}")
            }
        }
    }

    fun logout(token: String) {
        viewModelScope.launch {
            try {
                val response = authApi.logout("Bearer $token")
                if (response.isSuccessful) {
                    println("Logout successful.")
                } else {
                    println("Logout failed: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                println("Network error: ${e.message}")
            }
        }
    }
}

