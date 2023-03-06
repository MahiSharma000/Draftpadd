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
class BookViewModel :ViewModel(){
    private val _status = MutableLiveData<BookApiStatus>()
    val status: LiveData<BookApiStatus> = _status

    private var _selectedCategory = MutableLiveData<Category>()
    val selectedCategory: LiveData<Category> = _selectedCategory

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> = _categories

    private val _books = MutableLiveData<List<Book>>()
    val books: LiveData<List<Book>> = _books

    init {
        _status.value = BookApiStatus.LOADING
        _books.value = listOf()
        getBooks()
    }


    private fun getBooks() {
        viewModelScope.launch {
            _status.value = BookApiStatus.LOADING
            try {
                ApiClient.retrofitService.getBooks().let {
                    Log.d("BookViewModel", it.toString())
                    _books.value = it.books
                    if (it.books.isNotEmpty()) {
                        _status.value = BookApiStatus.DONE
                    } else {
                        _status.value = BookApiStatus.ERROR
                    }
                }
            } catch (e: Exception) {
                Log.e("BookViewModel", e.toString())
                _status.value = BookApiStatus.ERROR
                _categories.value = listOf()
            }
        }
    }

    fun setBooks(book: Book) {
        _books.value = listOf(book)
    }

}