package com.example.draftpad.ui.read

import android.os.Build
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.draftpad.network.*
import com.example.draftpad.ui.profile.AuthorApiStatus
import kotlinx.coroutines.launch
import java.time.LocalDateTime

enum class CommentApiStatus { LOADING, ERROR, DONE }
class CommentViewModel : ViewModel() {

    private val _status = MutableLiveData<CommentApiStatus>()
    val status: LiveData<CommentApiStatus> = _status

    private val _getresponse = MutableLiveData<GetCommentsResponse>()
    val getresponse: LiveData<GetCommentsResponse> = _getresponse

    private val _postResponse = MutableLiveData<PostCommentResponse>()
    val postResponse: LiveData<PostCommentResponse> = _postResponse

    private val _userstatus = MutableLiveData<AuthorApiStatus>()
    val userstatus: LiveData<AuthorApiStatus> = _userstatus

    private val _updateResponse=MutableLiveData<UpdateCommentsResponse>()
    val updateResponse:LiveData<UpdateCommentsResponse> = _updateResponse

    private val _user = MutableLiveData<UserProfile?>()
    val user: LiveData<UserProfile?> = _user

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
            val currDateTime = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                LocalDateTime.now()
            } else {
                System.currentTimeMillis()
            }
            val comment = Comment(
                id = 1,
                content = content,
                user_id = user_id,
                username = "",
                chapter_id = chapter_id,
                created_at = currDateTime.toString(),
                updated_at = currDateTime.toString(),
                ""
            )
            postComment(comment)
        } catch (e: Exception) {
            Log.d("Error", "createnewChapter: ${e.message}")
        }
    }

    private fun postComment(comment: Comment) {
        viewModelScope.launch {
            _status.value = CommentApiStatus.LOADING
            _postResponse.value = ApiClient.retrofitService.addComment(
                comment.content,
                comment.chapter_id,
                comment.user_id,
            )
            _status.value = CommentApiStatus.DONE
        }

    }
    fun getUserProfile(uid: Int) {
        viewModelScope.launch {
            _userstatus.value = AuthorApiStatus.LOADING
            try {
                ApiClient.retrofitService.getProfile(uid).let { response ->
                    Log.d("UserProfileViewModel", "getUserProfile: $response")
                    _user.value = response.author
                    _userstatus.value = AuthorApiStatus.DONE
                }

            } catch (e: Exception) {
                _userstatus.value = AuthorApiStatus.ERROR
                _user.value = null
                Log.e("UserProfileViewModel", "getUserProfile: $e")
            }
        }
    }

    fun updateComments(){
        viewModelScope.launch {
            _status.value = CommentApiStatus.LOADING
            _updateResponse.value = ApiClient.retrofitService.updateComments(
                _comId.value!!
            )
            _status.value = CommentApiStatus.DONE
        }
    }
}