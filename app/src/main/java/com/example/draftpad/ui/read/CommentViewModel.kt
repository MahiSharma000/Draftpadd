package com.example.draftpad.ui.read

import android.os.Build
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.draftpad.network.*
import com.example.draftpad.ui.profile.ProfileApiStatus
import com.example.draftpad.ui.profile.UserProfileApiStatus
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

    private val _userstatus = MutableLiveData<UserProfileApiStatus>()
    val userstatus: LiveData<UserProfileApiStatus> = _userstatus

    private val _chapterStatus = MutableLiveData<ReadStoryApiStatus>()
    val chapterStatus: LiveData<ReadStoryApiStatus> = _chapterStatus

    private val _chapterResponse = MutableLiveData<PostChapterResponse>()
    val chapterResponse: LiveData<PostChapterResponse> = _chapterResponse

    private val _chapter = MutableLiveData<Chapter>()
    val chapter: LiveData<Chapter> = _chapter

    private val _chapterId = MutableLiveData<Int>()
    val chapterId: LiveData<Int> = _chapterId

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
            _userstatus.value = UserProfileApiStatus.LOADING
            try {
                ApiClient.retrofitService.getProfile(uid).let { response ->
                    Log.d("UserProfileViewModel", "getUserProfile: $response")
                    _user.value = response.author
                    _userstatus.value = UserProfileApiStatus.DONE
                }

            } catch (e: Exception) {
                _userstatus.value = UserProfileApiStatus.ERROR
                _user.value = null
                Log.e("UserProfileViewModel", "getUserProfile: $e")
            }
        }
    }

    fun updateChapter(
        id:Int,
        bookId: Int,
        chapterTitle: String,
        chapterContent: String,
        status: Int,
        categoryid: Int,
        likes:Int,
        comments:Int,
        uid:Int
    ) {
        Log.d("Chapter", "createNewChapter: $bookId")
        val chapter = Chapter(
            id = id,
            book_Id = bookId,
            title = chapterTitle,
            content = chapterContent,
            category_id = categoryid,
            status = status,
            total_comments = comments,
            total_likes = likes,
            user_Id = uid,
            book_title = "",
            book_views = 0,
        )
        postChapter(chapter)
    }


    private fun postChapter(chapter: Chapter) {
        Log.d("Chapter", "postChapter: ${chapter.title}")
        viewModelScope.launch {
            _chapterStatus.value = ReadStoryApiStatus.LOADING
            _chapterResponse.value = ApiClient.retrofitService.createChapter(
                id = chapter.id,
                title = chapter.title,
                book_id = chapter.book_Id,
                content = chapter.content,
                category_id = chapter.category_id,
                user_id = chapter.user_Id,
                status = chapter.status,
                total_comments = chapter.total_comments,
                total_likes = chapter.total_likes,
            )
            _status.value = CommentApiStatus.DONE
        }
    }

    fun getchapter() {
        viewModelScope.launch {
            _chapterStatus.value = ReadStoryApiStatus.LOADING
            try {
                _chapterId.value?.let {
                    ApiClient.retrofitService.getChapter(it).let { response ->
                        Log.d("CommentViewModel", response.toString())
                        _chapter.value = response.chapter
                        if (response.chapter != null) {
                            _chapterStatus.value = ReadStoryApiStatus.DONE
                        } else {
                            _chapterStatus.value = ReadStoryApiStatus.ERROR
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("CommentViewModel", e.toString())
                _chapterStatus.value = ReadStoryApiStatus.ERROR
            }
        }
    }

    fun setChapter(chapterid: Int) {
        _chapterId.value = chapterid
    }

}