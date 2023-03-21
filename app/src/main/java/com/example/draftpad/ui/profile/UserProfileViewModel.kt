package com.example.draftpad.ui.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.draftpad.network.ApiClient
import com.example.draftpad.network.UserDataResponse
import com.example.draftpad.network.UserProfile
import kotlinx.coroutines.launch

enum class UserProfileApiStatus { LOADING, ERROR, DONE, NONE }

class UserProfileViewModel :ViewModel(){
    private val _status = MutableLiveData<UserProfileApiStatus>()
    val status: LiveData<UserProfileApiStatus> = _status

    private val _response = MutableLiveData<UserDataResponse>()
    val response: LiveData<UserDataResponse> = _response

    private val _user = MutableLiveData<UserProfile?>()
    val user: LiveData<UserProfile?> = _user

    fun getUserProfile(uid: Int) {
        viewModelScope.launch {
            _status.value = UserProfileApiStatus.LOADING
            try {
                ApiClient.retrofitService.getProfile(uid).let { response ->
                    Log.d("UserProfileViewModel", "getUserProfile: $response")
                    _user.value = response.author
                    _status.value = UserProfileApiStatus.DONE
                }

            } catch (e: Exception) {
                _status.value = UserProfileApiStatus.ERROR
                _user.value = null
                Log.e("UserProfileViewModel", "getUserProfile: $e")
            }
        }
    }
}