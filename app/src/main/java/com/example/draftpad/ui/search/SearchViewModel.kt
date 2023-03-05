package com.example.draftpad.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.draftpad.network.ApiClient
import com.example.draftpad.network.Category
import androidx.lifecycle.viewModelScope
import com.example.draftpad.network.Book
import kotlinx.coroutines.launch


enum class SearchApiStatus { LOADING, ERROR, DONE }
class SearchViewModel : ViewModel() {

    private val _selectedCategory = MutableLiveData<Category>()
    val selectedCategory: LiveData<Category> = _selectedCategory

    private val _status = MutableLiveData<SearchApiStatus>()
    val status: LiveData<SearchApiStatus> = _status

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> = _categories

    private val _books = MutableLiveData<List<Book>>()
    val books: LiveData<List<Book>> = _books

    init {
        _status.value = SearchApiStatus.LOADING
        _selectedCategory.value = Category(0, "Paranomal")
        getCategories()
    }

    private fun getCategories() {
        viewModelScope.launch {
            _status.value = SearchApiStatus.LOADING
            try {
                ApiClient.retrofitService.getCategories().let {
                    Log.d("SearchViewModel", it.toString())
                    _categories.value = it.categories
                    if (it.categories.isNotEmpty()) {
                        _status.value = SearchApiStatus.DONE
                    } else {
                        _status.value = SearchApiStatus.ERROR
                    }
                }
            } catch (e: Exception) {
                Log.e("SearchViewModel", e.toString())
                _status.value = SearchApiStatus.ERROR
                _categories.value = listOf()
            }
        }
    }

    fun setCategory(category: Category) {
        _selectedCategory.value = category
    }


}