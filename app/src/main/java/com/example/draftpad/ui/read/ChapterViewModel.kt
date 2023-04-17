package com.example.draftpad.ui.read

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.draftpad.network.ApiClient
import com.example.draftpad.network.Book
import com.example.draftpad.network.Chapter
import com.example.draftpad.network.UpdateBookViewsResponse
import kotlinx.coroutines.launch

enum class ChapterApiStatus { LOADING, ERROR, DONE }
class ChapterViewModel : ViewModel() {

    private val _status = MutableLiveData<ChapterApiStatus>()
    val status: LiveData<ChapterApiStatus> = _status

    private val _chapters = MutableLiveData<List<Chapter>>()
    val chapter: LiveData<List<Chapter>> = _chapters

    private val _updateResponse = MutableLiveData<UpdateBookViewsResponse>()
    val updateResponse: LiveData<UpdateBookViewsResponse> = _updateResponse

    private val _bookId = MutableLiveData<Int>()
    val bookId: LiveData<Int> = _bookId

    private val _books = MutableLiveData<Book>()
    val books: LiveData<Book> = _books

    init {
        _status.value = ChapterApiStatus.LOADING
    }

    fun getChapterOfBook() {
        viewModelScope.launch {
            _status.value = ChapterApiStatus.LOADING
            try {
                _bookId.value?.let {
                    ApiClient.retrofitService.getChapters(it).let { response ->
                        _chapters.value = response.chapters
                        if (response.chapters.isNotEmpty()) {
                            _status.value = ChapterApiStatus.DONE
                        } else {
                            _status.value = ChapterApiStatus.ERROR
                        }
                    }
                }
            } catch (e: Exception) {
                _status.value = ChapterApiStatus.ERROR
                _chapters.value = listOf()
            }
        }
    }

    fun setBookId(id: Int) {
        _bookId.value = id
    }

    fun updateViews(bookId: Int) {
        viewModelScope.launch {
            _updateResponse.value = ApiClient.retrofitService.updateViews(
                bookId,
            )
        }
    }
}