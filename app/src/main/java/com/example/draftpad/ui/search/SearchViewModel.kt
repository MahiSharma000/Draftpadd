package com.example.draftpad.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.draftpad.network.*
import kotlinx.coroutines.launch


enum class SearchApiStatus { LOADING, ERROR, DONE }
class SearchViewModel : ViewModel() {

    private val _status = MutableLiveData<SearchApiStatus>()
    val status: LiveData<SearchApiStatus> = _status

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> = _categories

    private val _query = MutableLiveData<String>()
    val query: LiveData<String> = _query

    private val _storyList = MutableLiveData<List<Book>>()
    val storyList = _storyList

    private val _profileList = MutableLiveData<List<UserProfile>>()
    val profileList = _profileList

    private val _readingList = MutableLiveData<List<ReadingList>>()
    val readingList = _readingList


    init {
        _status.value = SearchApiStatus.LOADING
        getCategories()
    }

    fun setQuery(query: String) {
        _query.value = query
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

    fun getSearchResult(query: String, filterName: String) {
        when (filterName) {
            "Stories" -> getStories(query)
            "Profiles" -> getProfiles(query)
            "Reading Lists" -> getReadingLists(query)
        }
    }

    private fun getReadingLists(query: String) {
        Log.d("SearchViewModel", "getReadingLists")
    }

    private fun getProfiles(query: String) {
        Log.d("SearchViewModel", "getProfiles")
    }

    private fun getStories(query: String) {
        Log.d("SearchViewModel", "getStories")

    }
}