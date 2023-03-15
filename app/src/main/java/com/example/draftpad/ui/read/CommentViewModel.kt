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

enum class CommentApiStatus { LOADING, ERROR, DONE }
class CommentViewModel : ViewModel() {

    private val _status = MutableLiveData<CommentApiStatus>()
    val status: LiveData<CommentApiStatus> = _status

    private val _getresponse = MutableLiveData<GetCommentsResponse>()
    val getresponse: LiveData<GetCommentsResponse> = _getresponse

    private val _postRespose = MutableLiveData<PostCommentResponse>()
    val postResponse: LiveData<PostCommentResponse> = _postRespose

    private val _comments = MutableLiveData<List<Comment>>()
    val comments: LiveData<List<Comment>> = _comments

    private val _comId = MutableLiveData<Int>()
    val comId: LiveData<Int> = _comId


    init {
        _status.value = CommentApiStatus.LOADING
        getAllComments()
    }

    fun getAllComments() {
        viewModelScope.launch {
            _status.value = CommentApiStatus.LOADING
            try {
                _comId.value?.let {
                    ApiClient.retrofitService.getComments(it).let { response ->
                        Log.d("CommentViewModel", response.toString())
                        _comments.value = response.comments
                        if (response.comments.isNotEmpty()) {
                            _status.value = CommentApiStatus.DONE
                        } else {
                            _status.value = CommentApiStatus.ERROR
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("CommentViewModel", e.toString())
                _status.value = CommentApiStatus.ERROR
                _comments.value = listOf()
            }
        }
    }

    fun setCommentId(id: Int) {
        _comId.value = id
    }

    fun createComment(content: String, chapter_id: Int, user_id: Int) {
        try {
            viewModelScope.launch {
                _status.value = CommentApiStatus.LOADING
                _postRespose.value = ApiClient.retrofitService.addComment(
                    content = content,
                    chapter_id = chapter_id,
                    user_id = user_id,

                )
                _status.value = CommentApiStatus.DONE
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}