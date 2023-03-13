package com.example.draftpad.ui.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.draftpad.network.ApiClient
import com.example.draftpad.network.Chapter
import com.example.draftpad.network.User
import com.example.draftpad.network.UserProfile
import com.example.draftpad.ui.read.ChapterApiStatus
import com.example.draftpad.ui.read.ReadStoryApiStatus
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

    fun getAuthorId() {
        viewModelScope.launch {
            _status.value = AuthorApiStatus.LOADING
            try {
                _userId.value?.let {
                    ApiClient.retrofitService.getProfile(it).let { response ->
                        _author.value = response.author
                        _status.value = AuthorApiStatus.DONE
                    }
                }
            } catch (e: Exception) {
                _status.value = AuthorApiStatus.ERROR
                _author.value = null
            }
        }
    }

    fun setAuthorId(id: Int) {
        _userId.value = id
    }
}