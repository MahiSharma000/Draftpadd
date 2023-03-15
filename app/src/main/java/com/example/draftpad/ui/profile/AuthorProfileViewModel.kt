package com.example.draftpad.ui.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.draftpad.network.ApiClient
import com.example.draftpad.network.UserProfile
import kotlinx.coroutines.launch

enum class AuthorApiStatus { LOADING, ERROR, DONE }
class AuthorProfileViewModel : ViewModel() {
    private val _status = MutableLiveData<AuthorApiStatus>()
    val status: LiveData<AuthorApiStatus> = _status

    private val _author = MutableLiveData<UserProfile?>()
    val author: LiveData<UserProfile?> = _author

    private val _userId = MutableLiveData<Int>()
    val userId: LiveData<Int> = _userId

    init {
        _status.value = AuthorApiStatus.LOADING
    }

    fun getAuthorId(uid: Int) {
        viewModelScope.launch {
            _status.value = AuthorApiStatus.LOADING
            try {
                setAuthorId(uid)
                ApiClient.retrofitService.getProfile(uid).let { response ->
                    Log.d("AuthorProfileViewModel", "getAuthorId: $response")
                    _author.value = response.author
                    _status.value = AuthorApiStatus.DONE
                }

            } catch (e: Exception) {
                _status.value = AuthorApiStatus.ERROR
                _author.value = null
                Log.e("AuthorProfileViewModel", "getAuthorId: $e")
            }
        }
    }

    private fun setAuthorId(id: Int) {
        _userId.value = id
    }
}