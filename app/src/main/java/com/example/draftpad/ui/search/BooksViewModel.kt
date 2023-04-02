package com.example.draftpad.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.draftpad.network.ApiClient
import com.example.draftpad.network.Book
import kotlinx.coroutines.launch

enum class BookApiStatus { LOADING, ERROR, DONE }
class BooksViewModel: ViewModel(){

    private val _status = MutableLiveData<BookApiStatus>()
    val status: LiveData<BookApiStatus> = _status

    private val _bookList = MutableLiveData<List<Book>>()
    val bookList: LiveData<List<Book>> = _bookList

    init {
        _status.value = BookApiStatus.LOADING

    }

    fun getBooks(catId : Int) {
        viewModelScope.launch {
            _status.value = BookApiStatus.LOADING
            try {
                ApiClient.retrofitService.getBooksByStatus(catId,1).let {response->
                    _status.value = BookApiStatus.DONE
                    _bookList.value = response.books
                    Log.d("SearchViewModel", response.toString())
                }
            } catch (e: Exception) {
                _status.value = BookApiStatus.ERROR
                _bookList.value = ArrayList()
            }
        }
    }
}