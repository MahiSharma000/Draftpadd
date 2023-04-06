package com.example.draftpad.ui.read

import android.os.Build
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.draftpad.network.*
import kotlinx.coroutines.launch
import java.time.LocalDateTime

enum class ReadApiStatus { LOADING, ERROR, DONE }

class ReadViewModel : ViewModel() {

    private val _status = MutableLiveData<ReadApiStatus>()
    val status: LiveData<ReadApiStatus> = _status

    private val _favouriteResponse = MutableLiveData<DownloadBookResponse>()
    val favouriteResponse: LiveData<DownloadBookResponse> = _favouriteResponse

    private val _book = MutableLiveData<Book?>()
    val book: LiveData<Book?> = _book

    private val _bookId = MutableLiveData<Int>(10)
    val bookId: LiveData<Int> = _bookId



    private val _readLater = MutableLiveData<AddToReadLaterResponse>()
    val readLater: LiveData<AddToReadLaterResponse> = _readLater

    init {
        _status.value = ReadApiStatus.LOADING
    }

    fun getSelectedBook() {
        Log.d("ReadViewModel",_bookId.value.toString())
        viewModelScope.launch {
            _status.value = ReadApiStatus.LOADING
            try {
                _bookId.value?.let {
                    ApiClient.retrofitService.getBook(it).let { response ->
                        _book.value = response.book
                        _status.value = ReadApiStatus.DONE
                        Log.d("ReadViewModel",_book.value.toString())
                    }
                }
            } catch (e: Exception) {
                Log.e("ReadViewModel", e.toString())
                _status.value = ReadApiStatus.ERROR
                _book.value = null
            }
        }
    }


    fun setBookId(id: Int) {
        _bookId.value = id
        Log.d("ReadViewModel",_bookId.value.toString())
    }

    fun downloadBook(userid: Int, bookId: Int){
        val currDateTime = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDateTime.now()
        } else {
            System.currentTimeMillis()
        }
        val download= Download(
            user_id = userid!!,
            book_id = bookId!!,
            created_at = currDateTime!!.toString(),
            updated_at = currDateTime!!.toString()
        )
        postDownload(download)
        }

    fun postDownload(download: Download){
        viewModelScope.launch {
            _favouriteResponse.value=ApiClient.retrofitService.download(
                user_id = download.user_id.toString(),
                book_id = download.book_id.toString(),
            )
        }
    }



    fun addreadlater( userId: Int,bookId: Int){
        viewModelScope.launch {
            _readLater.value=ApiClient.retrofitService.addReadLater(
                userId,
                bookId
            )
        }
    }

}