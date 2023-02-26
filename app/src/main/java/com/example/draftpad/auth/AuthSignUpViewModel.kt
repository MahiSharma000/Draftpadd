package com.example.draftpad.auth

import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.draftpad.network.ApiClient
import com.example.draftpad.network.User
import kotlinx.coroutines.launch
import java.time.LocalDateTime


enum class AuthApiStatus { LOADING, ERROR, DONE }
class AuthSignUpViewModel : ViewModel() {
    private val _status = MutableLiveData<AuthApiStatus>()
    val status: LiveData<AuthApiStatus> = _status

    private val _response = MutableLiveData<String>()
    val response: LiveData<String> = _response

    private fun createUser(user: User) {
        viewModelScope.launch {
            _status.value = AuthApiStatus.LOADING
            try {
                _response.value = ApiClient.retrofitService.register(user).toString()
                _status.value = AuthApiStatus.DONE
            } catch (e: Exception) {
                _status.value = AuthApiStatus.ERROR
                _response.value = e.message
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
            userEmail = userEmail!!,
            lastSeen = currDateTime.toString(),
            userPassword = userPassword!!
        )
        createUser(user)
    }

}