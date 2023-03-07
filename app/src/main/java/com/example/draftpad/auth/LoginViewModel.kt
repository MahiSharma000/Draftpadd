package com.example.draftpad.auth

import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.draftpad.Utils
import com.example.draftpad.models.LoggedUser
import com.example.draftpad.network.ApiClient
import com.example.draftpad.network.LoginResponse
import com.example.draftpad.network.RegisterResponse
import com.example.draftpad.network.User
import kotlinx.coroutines.launch
import java.time.LocalDateTime

enum class LoginApiStatus { LOADING, ERROR, DONE, NONE }
class LoginViewModel : ViewModel() {
    private val _status = MutableLiveData<LoginApiStatus>()
    val status: LiveData<LoginApiStatus> = _status

    private val _response = MutableLiveData<LoginResponse>()
    val response: LiveData<LoginResponse> = _response

    init {
        _status.value = LoginApiStatus.NONE
    }

    private fun checkUser(username: String?, password: String?) {
        viewModelScope.launch {
            _status.value = LoginApiStatus.LOADING
            try {
                _response.value = ApiClient.retrofitService.login(
                    username!!,
                    password!!
                )
                _status.value = LoginApiStatus.DONE
            } catch (e: Exception) {
                _status.value = LoginApiStatus.ERROR
                _response.value = LoginResponse("Error", username = "", email = "", id = "")
            }
        }
    }

    fun LoginUser(userName: String?, userPassword: String?) {
        checkUser(userName, userPassword)
    }

}