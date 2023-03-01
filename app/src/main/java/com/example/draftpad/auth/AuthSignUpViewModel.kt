package com.example.draftpad.auth

import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.draftpad.network.ApiClient
import com.example.draftpad.network.RegisterResponse
import com.example.draftpad.network.User
import kotlinx.coroutines.launch
import java.time.LocalDateTime


enum class AuthApiStatus { LOADING, ERROR, DONE, NONE }
class AuthSignUpViewModel : ViewModel() {
    private val _status = MutableLiveData<AuthApiStatus>()
    val status: LiveData<AuthApiStatus> = _status

    private val _response = MutableLiveData<RegisterResponse>()
    val response: LiveData<RegisterResponse> = _response

    init {
        _status.value = AuthApiStatus.NONE
    }

    private fun createUser(user: User) {
        viewModelScope.launch {
            _status.value = AuthApiStatus.LOADING
            try {
                _response.value = ApiClient.retrofitService.register(
                    user.userName,
                    user.email,
                    user.password,
                    user.lastSeen
                )
                _status.value = AuthApiStatus.DONE
            } catch (e: Exception) {
                _status.value = AuthApiStatus.ERROR
                _response.value = RegisterResponse("Error", e.message.toString())
            }
        }
    }

    fun signUpUser(userName: String?, userEmail: String?, userPassword: String?) {
        val currDateTime = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDateTime.now()
        } else {
            System.currentTimeMillis()
        }
        val user = User(
            id = null,
            userName = userName!!,
            email = userEmail!!,
            lastSeen = currDateTime.toString(),
            password = userPassword!!
        )
        createUser(user)
    }

}