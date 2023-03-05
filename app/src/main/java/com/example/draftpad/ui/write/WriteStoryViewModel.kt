package com.example.draftpad.ui.write

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.draftpad.auth.AuthApiStatus
import com.example.draftpad.network.ApiClient
import com.example.draftpad.network.CreateBookResponse
import com.example.draftpad.network.RegisterResponse
import com.example.draftpad.network.User
import kotlinx.coroutines.launch

enum class WriteStoryApiStatus { LOADING, ERROR, DONE, NONE }

class WriteStoryViewModel :ViewModel() {
    private val _status = MutableLiveData<WriteStoryApiStatus>()
    val status: LiveData<WriteStoryApiStatus> = _status

    private val _response = MutableLiveData<CreateBookResponse>()
    val response: LiveData<CreateBookResponse> = _response

    init {
        _status.value = WriteStoryApiStatus.NONE
    }

    /*private fun createBook(user: User) {
        viewModelScope.launch {
            _status.value = WriteStoryApiStatus.LOADING
            try {
                _response.value = ApiClient.retrofitService.createBook(
                    user.userName,
                    user.email,

                )
                _status.value = WriteStoryApiStatus.DONE
            } catch (e: Exception) {
                _status.value = WriteStoryApiStatus.ERROR
                _response.value = CreateBookResponse("Error", e.message.toString())
            }
        }
    }*/


}