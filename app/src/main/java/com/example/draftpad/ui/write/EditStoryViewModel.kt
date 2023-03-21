package com.example.draftpad.ui.write

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.draftpad.network.ApiClient
import com.example.draftpad.network.Book
import kotlinx.coroutines.launch


enum class EditStoryApiStatus { LOADING, ERROR, DONE }

class EditStoryViewModel : ViewModel(){
    private val _status = MutableLiveData<EditStoryApiStatus>()
    val status: LiveData<EditStoryApiStatus> = _status

    private val _books = MutableLiveData<List<Book>>()
    val books: LiveData<List<Book>> = _books

    init {
        _status.value = EditStoryApiStatus.LOADING
    }

    fun getBooksByStatus(userId:Int , bookStatus:Int) {
        viewModelScope.launch {
            _status.value = EditStoryApiStatus.LOADING
            try {
                userId.let {
                    ApiClient.retrofitService.getBooksByStatus(userId, bookStatus).let { response ->
                        Log.d("EditStoryViewModel", response.toString())
                        _books.value = response.books
                        if (response.books.isNotEmpty()) {
                            _status.value = EditStoryApiStatus.DONE
                        } else {
                            _status.value = EditStoryApiStatus.ERROR
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("EditStoryViewModel", e.toString())
                _status.value = EditStoryApiStatus.ERROR
                _books.value = listOf()
            }
        }
    }
}