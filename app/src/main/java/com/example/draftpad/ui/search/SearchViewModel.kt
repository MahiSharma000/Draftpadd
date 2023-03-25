package com.example.draftpad.ui.search

import android.util.Log
import android.widget.Toast
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

    private val _profileStatus = MutableLiveData<SearchApiStatus>()
    val profileStatus: LiveData<SearchApiStatus> = _profileStatus

    private val _storyStatus = MutableLiveData<SearchApiStatus>()
    val storyStatus: LiveData<SearchApiStatus> = _storyStatus

    private val _readingListStatus = MutableLiveData<SearchApiStatus>()
    val readingListStatus: LiveData<SearchApiStatus> = _readingListStatus

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> = _categories

    private val _query = MutableLiveData<String>()
    val query: LiveData<String> = _query

    private val _bookList = MutableLiveData<List<Book>>()
    val bookList: LiveData<List<Book>> = _bookList

    private val _profileList = MutableLiveData<List<UserProfile>>()
    val profileList: LiveData<List<UserProfile>> = _profileList

    private val _readingList = MutableLiveData<List<ReadingList>>()
    val readingList: LiveData<List<ReadingList>> = _readingList


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
        Log.d("SearchViewModel", "getSearchResult: $query, $filterName")
        when (filterName) {
            "stories" -> getStories(query)
            "profiles" -> getProfiles(query)
            "reading lists" -> getReadingLists(query)
        }
    }

    private fun getReadingLists(query: String) {
        Log.d("SearchViewModel", "getReadingLists")
    }

    private fun getProfiles(query: String) {
        viewModelScope.launch {
            _profileStatus.value = SearchApiStatus.LOADING
            try {
                ApiClient.retrofitService.getProfilesByName(query).let { response ->
                    Log.d("SearchNextViewModel", response.toString())
                    _profileList.value = response.profiles
                    if (response.profiles.isNotEmpty()) {
                        _profileStatus.value = SearchApiStatus.DONE
                    } else {
                        _profileStatus.value = SearchApiStatus.ERROR
                    }
                }

            } catch (e: Exception) {
                Log.e("SearchNextViewModel", e.toString())
                _profileStatus.value = SearchApiStatus.ERROR
                _profileList.value = listOf()
            }
        }

    }

    private fun getStories(query: String) {
        viewModelScope.launch {
            _storyStatus.value = SearchApiStatus.LOADING
            try {
                ApiClient.retrofitService.getBooksByName(query).let { response ->
                    Log.d("SearchNextViewModel", response.toString())
                    _bookList.value = response.books
                    if (response.books.isNotEmpty()) {
                        _storyStatus.value = SearchApiStatus.DONE
                    } else {
                        _storyStatus.value = SearchApiStatus.ERROR
                    }
                }

            } catch (e: Exception) {
                Log.e("SearchNextViewModel", e.toString())
                _storyStatus.value = SearchApiStatus.ERROR
                _bookList.value = listOf()
            }
        }

    }

    fun clearSearchResult() {
        _bookList.value = listOf()
        _profileList.value = listOf()
        _readingList.value = listOf()
    }

}