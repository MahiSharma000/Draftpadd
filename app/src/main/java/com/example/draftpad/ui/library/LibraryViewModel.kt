package com.example.draftpad.ui.library

import android.util.Log
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

    private val _downloadBooks = MutableLiveData<List<Book>>()
    val downloadBooks: LiveData<List<Book>> = _downloadBooks

    private val _readLaterBooks = MutableLiveData<List<Book>>()
    val readLaterBooks: LiveData<List<Book>> = _readLaterBooks

    init {
        _status.value = LibraryApiStatus.LOADING
    }

    fun getResult( filterName: String, uid:Int) {
        when (filterName) {
            //"Download" -> readLater(uid)
           "Read Later" -> readLater(uid)
        }
    }

    private fun readLater(userId : Int){
        viewModelScope.launch {
            _status.value = LibraryApiStatus.LOADING
            try {
                userId.let {
                    ApiClient.retrofitService.getReadingList(userId).let { response ->
                        _readLaterBooks.value = response.readLater
                        Log.d("Readlater", response.toString())
                        if (response.readLater.isNotEmpty()) {
                            _status.value = LibraryApiStatus.DONE
                        } else {
                            _status.value = LibraryApiStatus.ERROR
                        }


                    }
                }
            } catch (e: Exception) {
                Log.e("ReadLater", e.toString())
                _status.value = LibraryApiStatus.ERROR
            }
        }
    }

}