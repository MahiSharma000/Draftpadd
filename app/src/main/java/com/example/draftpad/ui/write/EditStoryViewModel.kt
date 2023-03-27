package com.example.draftpad.ui.write

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.draftpad.R
import com.example.draftpad.Utils
import com.example.draftpad.databinding.FragmentEditStoryBinding
import com.example.draftpad.databinding.FragmentSearchResultBinding
import com.example.draftpad.network.ApiClient
import com.example.draftpad.network.Book
import com.example.draftpad.ui.search.BookAdapter
import com.example.draftpad.ui.search.SearchNextFragmentDirections
import com.example.draftpad.ui.search.SearchViewModel
import kotlinx.coroutines.launch


enum class EditStoryApiStatus { LOADING, ERROR, DONE }

class EditStoryViewModel : ViewModel(){
    private val _status = MutableLiveData<EditStoryApiStatus>()
    val status: LiveData<EditStoryApiStatus> = _status

    private val _books = MutableLiveData<List<Book>>()
    val books: LiveData<List<Book>> = _books

   private val _draftbooks = MutableLiveData<List<Book>>()
    val draftbooks: LiveData<List<Book>> = _draftbooks

    private val _publishedbooks = MutableLiveData<List<Book>>()
    val publishedbooks: LiveData<List<Book>> = _publishedbooks

    init {
        _status.value = EditStoryApiStatus.LOADING
    }

    fun getResult( filterName: String, uid:Int) {
        Log.d("SearchViewModel", "getSearchResult: $filterName")
        when (filterName) {
            "published" -> getBooksByStatus(uid,1)
            "drafts" -> getBooksByStatus(uid,0)
        }
    }

    fun getBooksByStatus(userId:Int , bookStatus:Int) {
        viewModelScope.launch {
            _status.value = EditStoryApiStatus.LOADING
            try {
                userId.let {
                    ApiClient.retrofitService.getBooksByStatus(userId, bookStatus).let { response ->
                        Log.d("EditStoryViewModel", response.toString())
                        if(bookStatus==1){
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
                Log.e("EditStoryViewModel", e.toString())
                _status.value = EditStoryApiStatus.ERROR
            }
        }
    }
}

