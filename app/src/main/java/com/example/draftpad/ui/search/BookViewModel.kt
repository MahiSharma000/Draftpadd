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

    private val _catId = MutableLiveData<Int>()
    val catId: LiveData<Int> = _catId

    init {
        _status.value = BookApiStatus.LOADING
    }

    // get books of selected category
    fun getBooksOfCategory() {
        viewModelScope.launch {
            _status.value = BookApiStatus.LOADING
            try {
                _catId.value?.let {
                    ApiClient.retrofitService.getCategoryBooks(it).let { response ->
                        Log.d("BookViewModel", response.toString())
                        _books.value = response.books
                        if (response.books.isNotEmpty()) {
                            _status.value = BookApiStatus.DONE
                        } else {
                            _status.value = BookApiStatus.ERROR
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("BookViewModel", e.toString())
                _status.value = BookApiStatus.ERROR
                _books.value = listOf()
            }
        }
    }

    //set books of selected category

    fun setCategoryId(id: Int) {
        _catId.value = id
    }

    /* private fun getBooks() {
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
     }*/

}