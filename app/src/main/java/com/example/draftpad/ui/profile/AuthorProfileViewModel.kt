package com.example.draftpad.ui.profile

import android.os.Build
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.draftpad.network.AddFollowerResponse
import com.example.draftpad.network.ApiClient
import com.example.draftpad.network.Follower
import com.example.draftpad.network.UserProfile
import kotlinx.coroutines.launch
import java.time.LocalDateTime

enum class AuthorApiStatus { LOADING, ERROR, DONE }
class AuthorProfileViewModel : ViewModel() {
    private val _status = MutableLiveData<AuthorApiStatus>()
    val status: LiveData<AuthorApiStatus> = _status

    val _author = MutableLiveData<UserProfile?>()
    val author: LiveData<UserProfile?> = _author

    private val _userId = MutableLiveData<Int>()
    val userId: LiveData<Int> = _userId

    private val _response = MutableLiveData<AddFollowerResponse>()
    val response: LiveData<AddFollowerResponse> = _response

    private val _followed = MutableLiveData<Follower>()
    val followed: LiveData<Follower> = _followed

    init {
        _status.value = AuthorApiStatus.LOADING
    }

    fun getAuthorId(uid: Int) {
        viewModelScope.launch {
            _status.value = AuthorApiStatus.LOADING
            try {
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


    fun followAuthor(follower: Follower) {
        viewModelScope.launch {
            _status.value = AuthorApiStatus.LOADING
            _response.value = ApiClient.retrofitService.follow(
                follower.user_id,
                follower.follower_id
            )
        }
    }

    fun postFollow(
        userId: String,
        followerId: String
    ) {
        val currDateTime = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDateTime.now()
        } else {
            System.currentTimeMillis()
        }
        val follower = Follower(
            user_id = userId,
            follower_id = followerId,
            created_at = currDateTime.toString(),
            updated_at = currDateTime.toString()
        )
        followAuthor(follower)
    }


}