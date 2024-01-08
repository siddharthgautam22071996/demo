package com.example.alle.helper

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class LoadingManager {
    private val _isLoading = MutableLiveData<Boolean>()

    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun showLoading() {
        if (_isLoading.value?.not() == true)
            _isLoading.value = true
    }

    fun hideLoading() {
        if (_isLoading.value?.not() == false)
            _isLoading.value = false
    }
}