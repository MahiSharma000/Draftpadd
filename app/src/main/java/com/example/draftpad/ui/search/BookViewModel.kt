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

    private val _selectedCategory = MutableLiveData<Category>()
    val selectedCategory: LiveData<Category> = _selectedCategory

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> = _categories

    private val _books = MutableLiveData<List<Book>>()
    val books: LiveData<List<Book>> = _books

    init {
        _status.value = BookApiStatus.LOADING
        getSelectedCategoryBooks()
    }


    fun getSelectedCategoryBooks() {
        _selectedCategory.value?.let {
            viewModelScope.launch {
                _status.value = BookApiStatus.LOADING
                try {
                    ApiClient.retrofitService.getCategoryBooks(it.id).let {
                        Log.d("SearchViewModel", it.toString())
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
                    _books.value = listOf()
                }
            }
        }
    }
}