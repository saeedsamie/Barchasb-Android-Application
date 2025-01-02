package com.example.barchasb.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.barchasb.api.ApiClient
import com.example.barchasb.api.UserApi
import com.example.barchasb.api.UserProfile
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    private val _userProfile = MutableLiveData<UserProfile?>()
    val userProfile: LiveData<UserProfile?> get() = _userProfile

    fun fetchUserProfile(token: String) {
        viewModelScope.launch {
            val response = ApiClient.create<UserApi>().getUserProfile("Bearer $token")
            if (response.isSuccessful) {
                _userProfile.postValue(response.body())
            } else {
                _userProfile.postValue(null)
                // Handle error (e.g., show a message)
            }
        }
    }
}