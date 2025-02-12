package com.example.barchasb.leaderboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.barchasb.api.ApiClient
import com.example.barchasb.api.LeaderboardEntry
import com.example.barchasb.api.UserApi
import kotlinx.coroutines.launch

class LeaderboardViewModel : ViewModel() {
    private val _leaderboardEntries = MutableLiveData<List<LeaderboardEntry>>()
    val leaderboardEntries: LiveData<List<LeaderboardEntry>> = _leaderboardEntries

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val userApi = ApiClient.create<UserApi>()

    fun fetchLeaderboard(token: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null
                val response = userApi.getLeaderboard("Bearer $token")
                if (response.isSuccessful) {
                    _leaderboardEntries.value = response.body()
                } else {
                    _error.value = "Failed to fetch leaderboard"
                }
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
} 