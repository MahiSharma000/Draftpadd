package com.example.draftpad.ui.read

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.draftpad.network.ApiClient
import com.example.draftpad.network.Book
import kotlinx.coroutines.launch

enum class ReadApiStatus { LOADING, ERROR, DONE }

class ReadViewModel : ViewModel() {

    private val _status = MutableLiveData<ReadApiStatus>()
    val status: LiveData<ReadApiStatus> = _status

    private val _book = MutableLiveData<Book?>()
    val book: LiveData<Book?> = _book

    private val _bookId = MutableLiveData<Int>(10)
    val bookId: LiveData<Int> = _bookId

    init {
        _status.value = ReadApiStatus.LOADING
    }

    fun getSelectedBook() {
        Log.d("ReadViewModel",_bookId.value.toString())
        viewModelScope.launch {
            _status.value = ReadApiStatus.LOADING
            try {
                _bookId.value?.let {
                    ApiClient.retrofitService.getBook(it).let { response ->
                        _book.value = response.book
                        _status.value = ReadApiStatus.DONE
                        Log.d("ReadViewModel",_book.value.toString())
                    }
                }
            } catch (e: Exception) {
                Log.e("ReadViewModel", e.toString())
                _status.value = ReadApiStatus.ERROR
                _book.value = null
            }
        }
    }


    fun setBookId(id: Int) {
        _bookId.value = id
        Log.d("ReadViewModel",_bookId.value.toString())
    }

}