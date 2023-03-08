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

    private val _bookId = MutableLiveData<Int>()
    val bookId: LiveData<Int> = _bookId

    init {
        _status.value = ReadApiStatus.LOADING
    }

    // get book of selected id
    fun getSelectedBook() {
        viewModelScope.launch {
            _status.value = ReadApiStatus.LOADING
            try {
                _bookId.value?.let {
                    ApiClient.retrofitService.getBook(it).let { response ->
                        Log.d("ReadViewModel", response.toString())
                        _book.value = response.book
                        if (response.book != null){
                            _status.value = ReadApiStatus.DONE
                        } else {
                            _status.value = ReadApiStatus.ERROR
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("ReadViewModel", e.toString())
                _status.value = ReadApiStatus.ERROR
                _book.value = null
            }
        }
    }

    //set book of selected id

    fun setBookId(id: Int) {
        _bookId.value = id
    }

}