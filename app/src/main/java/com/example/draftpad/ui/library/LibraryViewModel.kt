package com.example.draftpad.ui.library

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.draftpad.network.ApiClient
import com.example.draftpad.network.Book
import kotlinx.coroutines.launch

enum class LibraryApiStatus { LOADING, ERROR, DONE }
class LibraryViewModel : ViewModel() {

    private val _status = MutableLiveData<LibraryApiStatus>()
    val status: LiveData<LibraryApiStatus> = _status

    private val _favStatus = MutableLiveData<LibraryApiStatus>()
    val favStatus: LiveData<LibraryApiStatus> = _favStatus

    private val _favouriteBooks = MutableLiveData<List<Book>>()
    val favouriteBooks: LiveData<List<Book>> = _favouriteBooks

    private val _readLaterBooks = MutableLiveData<List<Book>>()
    val readLaterBooks: LiveData<List<Book>> = _readLaterBooks

    init {
        _status.value = LibraryApiStatus.LOADING
    }

    fun getResult(filterName: String, uid: Int) {
        when (filterName) {
            "Favourites" -> favourites(uid)
            "Read Later" -> readLater(uid)
        }
    }

    private fun readLater(userId: Int) {
        viewModelScope.launch {
            _status.value = LibraryApiStatus.LOADING
            try {
                userId.let {
                    ApiClient.retrofitService.getReadingList(userId).let { response ->
                        _readLaterBooks.value = response.readLater
                        if (response.readLater.isNotEmpty()) {
                            _status.value = LibraryApiStatus.DONE
                        } else {
                            _status.value = LibraryApiStatus.ERROR
                        }
                    }
                }
            } catch (e: Exception) {
                _status.value = LibraryApiStatus.ERROR
            }
        }
    }

    private fun favourites(userId: Int) {
        viewModelScope.launch {
            _favStatus.value = LibraryApiStatus.LOADING
            try {
                userId.let {
                    ApiClient.retrofitService.getFavourite(userId).let { response ->
                        _favouriteBooks.value = response.favourite
                        if (response.favourite.isNotEmpty()) {
                            _favStatus.value = LibraryApiStatus.DONE
                        } else {
                            _favStatus.value = LibraryApiStatus.ERROR
                        }


                    }
                }
            } catch (e: Exception) {
                _favStatus.value = LibraryApiStatus.ERROR
            }
        }
    }

}