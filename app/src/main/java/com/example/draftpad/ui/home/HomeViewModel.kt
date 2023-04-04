package com.example.draftpad.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.draftpad.network.ApiClient
import com.example.draftpad.network.Book
import com.example.draftpad.ui.search.BookApiStatus
import kotlinx.coroutines.launch

enum class HomeApiStatus { LOADING, ERROR, DONE }
class HomeViewModel : ViewModel() {

    private val _status = MutableLiveData<HomeApiStatus>()
    val status: LiveData<HomeApiStatus> = _status

    private val _books = MutableLiveData<List<Book>>()
    val books: LiveData<List<Book>> = _books

    init {
        _status.value = HomeApiStatus.LOADING
    }

    fun getBooks() {
        viewModelScope.launch {
            _status.value = HomeApiStatus.LOADING
            try {
                    ApiClient.retrofitService.getBooksByMaxViews().let { response ->
                        _books.value = response.books
                        if (response.books.isNotEmpty()) {
                            _status.value = HomeApiStatus.DONE
                        } else {
                            _status.value = HomeApiStatus.ERROR
                        }
                    }

            } catch (e: Exception) {
                Log.e("HomeViewModel", "getBooks: ${e.message}")
                _status.value = HomeApiStatus.ERROR
                _books.value = listOf()
            }
        }
    }
}