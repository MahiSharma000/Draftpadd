package com.example.draftpad.ui.write

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.draftpad.network.ApiClient
import com.example.draftpad.network.Book
import com.example.draftpad.network.Category
import kotlinx.coroutines.launch


enum class EditStoryApiStatus { LOADING, ERROR, DONE }

class EditStoryViewModel : ViewModel() {
    private val _status = MutableLiveData<EditStoryApiStatus>()
    val status: LiveData<EditStoryApiStatus> = _status

    private val _books = MutableLiveData<List<Book>>()
    val books: LiveData<List<Book>> = _books

    private val cat = MutableLiveData<Category>()
    val category: LiveData<Category> = cat

    private val _draftbooks = MutableLiveData<List<Book>>()
    val draftbooks: LiveData<List<Book>> = _draftbooks

    private val _publishedbooks = MutableLiveData<List<Book>>()
    val publishedbooks: LiveData<List<Book>> = _publishedbooks

    init {
        _status.value = EditStoryApiStatus.LOADING
    }

    fun getResult(filterName: String, uid: Int) {
        when (filterName) {
            "published" -> getBooksByStatus(uid, 1)
            "drafts" -> getBooksByStatus(uid, 0)
        }
    }

    fun getBooksByStatus(userId: Int, bookStatus: Int) {
        viewModelScope.launch {
            _status.value = EditStoryApiStatus.LOADING
            try {
                userId.let {
                    ApiClient.retrofitService.getBooksByStatus(userId, bookStatus).let { response ->
                        if (bookStatus == 1) {
                            _publishedbooks.value = response.books

                        } else {
                            _draftbooks.value = response.books
                        }

                        if (response.books.isNotEmpty()) {
                            _status.value = EditStoryApiStatus.DONE
                        } else {
                            _status.value = EditStoryApiStatus.ERROR
                        }
                    }
                }
            } catch (e: Exception) {
                _status.value = EditStoryApiStatus.ERROR
            }
        }
    }
}

