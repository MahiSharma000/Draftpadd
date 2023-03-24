package com.example.draftpad.ui.search


import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.draftpad.network.*
import kotlinx.coroutines.launch



enum class ProfileApiStatus { LOADING, ERROR, DONE }

class ProfileViewModel : ViewModel() {
    private val _status = MutableLiveData<ProfileApiStatus>()
    val status: LiveData<ProfileApiStatus> = _status

    private val _response = MutableLiveData<ProfilesByNameResponse>()
    val response: LiveData<ProfilesByNameResponse> = _response

    private val _profiles = MutableLiveData<List<UserProfile>>()
    val profiles: LiveData<List<UserProfile>> = _profiles

    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name

    init {
        _status.value = ProfileApiStatus.LOADING
        getProfiles()
    }

    fun getProfiles() {
        viewModelScope.launch {
            _status.value = ProfileApiStatus.LOADING
            try {
                _name.value?.let {
                    ApiClient.retrofitService.getProfilesByName(it).let { response ->
                        Log.d("ProfileViewModel", response.toString())
                        _profiles.value = response.profiles
                        if (response.profiles.isNotEmpty()) {
                            _status.value = ProfileApiStatus.DONE
                        } else {
                            _status.value = ProfileApiStatus.ERROR
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("CommentViewModel", e.toString())
                _status.value = ProfileApiStatus.ERROR
                _profiles.value = listOf()
            }
        }
    }

    fun setName(name: String) {
        _name.value = name

    }
}