package com.example.barchasb.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.barchasb.api.ApiClient
import com.example.barchasb.api.AuthApi
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val authApi = ApiClient.create<AuthApi>()

    fun login(username: String, password: String) {
        viewModelScope.launch {
            try {
                val response = authApi.login(username, password)
                if (response.isSuccessful) {
                    // Handle success
                    val message = response.body()?.get("message")
                    println("Login successful: $message")
                } else {
                    // Handle error
                    println("Login failed: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                println("Network error: ${e.message}")
            }
        }
    }
}
