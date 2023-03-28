package com.example.draftpad.ui.read

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.draftpad.network.ApiClient
import com.example.draftpad.network.Book
import com.example.draftpad.network.Chapter
import kotlinx.coroutines.launch

enum class ChapterApiStatus { LOADING, ERROR, DONE }
class ChapterViewModel:ViewModel() {

    private val _status = MutableLiveData<ChapterApiStatus>()
    val status: LiveData<ChapterApiStatus> = _status

    private val _chapters = MutableLiveData<List<Chapter>>()
    val chapter: LiveData<List<Chapter>> = _chapters

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
                        Log.d("ChapterViewModel", response.toString())
                        _chapters.value = response.chapters
                        if (response.chapters.isNotEmpty()) {
                            _status.value = ChapterApiStatus.DONE
                        } else {
                            _status.value = ChapterApiStatus.ERROR
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("ChapterViewModel", e.toString())
                _status.value = ChapterApiStatus.ERROR
                _chapters.value = listOf()
            }
        }
    }

    fun setBookId(id: Int) {
        _bookId.value = id
    }
}