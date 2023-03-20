package com.example.draftpad.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.draftpad.network.ApiClient
import com.example.draftpad.network.Book
import com.example.draftpad.network.Category
import kotlinx.coroutines.launch

enum class BookApiStatus { LOADING, ERROR, DONE }
class BookViewModel : ViewModel() {
    private val _status = MutableLiveData<BookApiStatus>()
    val status: LiveData<BookApiStatus> = _status

    private val _books = MutableLiveData<List<Book>>()
    val books: LiveData<List<Book>> = _books

    private val _book = MutableLiveData<Book>()
    val book: LiveData<Book> = _book

    private val _catId = MutableLiveData<Int>()
    val catId: LiveData<Int> = _catId

    private val _statusBook = MutableLiveData<Int>()
    val statusBook: LiveData<Int> = _statusBook

    init {
        _status.value = BookApiStatus.LOADING
    }


    fun getBooksOfCategory() {
        viewModelScope.launch {
            _status.value = BookApiStatus.LOADING
            try {
                _catId.value?.let {
                    ApiClient.retrofitService.getCategoryBooks(it).let { response ->
                        _books.value = response.books
                        if (response.books.isNotEmpty()) {
                            _status.value = BookApiStatus.DONE
                        } else {
                            _status.value = BookApiStatus.ERROR
                        }
                    }
                }
            } catch (e: Exception) {
                _status.value = BookApiStatus.ERROR
                _books.value = listOf()
            }
        }
    }
    fun setCategoryId(id: Int) {
        _catId.value = id
    }

}