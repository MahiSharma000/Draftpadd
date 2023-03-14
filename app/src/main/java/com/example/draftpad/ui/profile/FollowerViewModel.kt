package com.example.draftpad.ui.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.draftpad.network.ApiClient
import com.example.draftpad.network.Follower



import kotlinx.coroutines.launch

enum class FollowerApiStatus { LOADING, ERROR, DONE }

class FollowerViewModel:ViewModel(){
    private val _status = MutableLiveData<FollowerApiStatus>()
    val status: LiveData<FollowerApiStatus> = _status

    private val _followers = MutableLiveData<List<Follower>>()
    val followers: LiveData<List<Follower>> = _followers

    private val _userId = MutableLiveData<Int>()
    val userId: LiveData<Int> = _userId

    init {
        _status.value = FollowerApiStatus.LOADING
        getFollowers()
    }

    fun getFollowers() {
        viewModelScope.launch {
            _status.value = FollowerApiStatus.LOADING
            try {
                _userId.value?.let {
                    ApiClient.retrofitService.getFollowers(it).let { response ->
                        Log.d("FollowerViewModel", response.toString())
                        _followers.value = response.followers
                        if (response.followers.isNotEmpty()) {
                            _status.value = FollowerApiStatus.DONE
                        } else {
                            _status.value = FollowerApiStatus.ERROR
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("FollowerViewModel", e.toString())
                _status.value = FollowerApiStatus.ERROR
                _followers.value = listOf()
            }
        }
    }

    fun setUserId(id: Int) {
        _userId.value = id
    }
}