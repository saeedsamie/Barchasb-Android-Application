package com.example.barchasb.tasks

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.barchasb.api.ApiClient
import com.example.barchasb.api.Task
import com.example.barchasb.api.TaskApi
import kotlinx.coroutines.launch

class TaskViewModel : ViewModel() {

    private val taskApi = ApiClient.create<TaskApi>()

    private val _tasks = MutableLiveData<List<Task>?>()
    val tasks: LiveData<List<Task>?> get() = _tasks

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    fun fetchTasks(apiToken: String, limit: Int = 2) {
        _isLoading.value = true
        _error.value = null
        viewModelScope.launch {
            try {
                val response = taskApi.getTaskFeed("Bearer $apiToken", limit)
                if (response.isSuccessful) {
                    _tasks.value = response.body()
                } else {
                    _error.value = "Failed to fetch tasks: ${response.message()}"
                }
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearError() {
        _error.value = null
    }
}
