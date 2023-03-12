package com.example.draftpad.ui.read

import android.os.Build
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.draftpad.network.ApiClient
import com.example.draftpad.network.Comment
import com.example.draftpad.network.CommentResponse
import com.example.draftpad.network.CommentsResponse
import kotlinx.coroutines.launch
import java.time.LocalDateTime

enum class CommentApiStatus { LOADING, ERROR, DONE }
class CommentViewModel : ViewModel() {

    private val _status = MutableLiveData<CommentApiStatus>()
    val status: LiveData<CommentApiStatus> = _status

    private val _response = MutableLiveData<CommentsResponse>()
    val response: LiveData<CommentsResponse> = _response

    private val _postRespose = MutableLiveData<CommentResponse>()
    val postResponse: LiveData<CommentResponse> = _postRespose

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

    private fun addComment(comment: Comment){
        viewModelScope.launch {
            _status.value = CommentApiStatus.LOADING
            try {
                _response.value = ApiClient.retrofitService.addComment(
                    comment.content,
                    comment.chapter_id,
                    comment.user_id,
                    comment.created_at,
                    comment.updated_at
                )
            } catch (e: Exception) {
                Log.e("CommentViewModel", e.toString())
                _status.value = CommentApiStatus.ERROR
                _comments.value = listOf()
            }
        }

    }
    fun createComment(content: String, chapter_id: Int, user_id: Int){
        val currDateTime = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDateTime.now()
        } else {
            System.currentTimeMillis()
        }
        val comment = Comment(
            id= null,
            content = content!!,
            chapter_id = chapter_id!!,
            user_id = user_id!!,
            created_at = currDateTime.toString(),
            updated_at = currDateTime.toString(),
            )
        addComment(comment)
    }

}