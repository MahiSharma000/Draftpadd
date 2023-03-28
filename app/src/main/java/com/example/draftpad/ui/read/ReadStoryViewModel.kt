package com.example.draftpad.ui.read

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.draftpad.network.*
import com.example.draftpad.ui.write.WriteApiStatus
import kotlinx.coroutines.launch

enum class ReadStoryApiStatus { LOADING, ERROR, DONE }
class ReadStoryViewModel : ViewModel() {
    private val _status = MutableLiveData<ReadStoryApiStatus>()
    val status: LiveData<ReadStoryApiStatus> = _status

    private val _chapter = MutableLiveData<Chapter>()
    val chapter: LiveData<Chapter> = _chapter

    private val _chapterId = MutableLiveData<Int>()
    val chapterId: LiveData<Int> = _chapterId

    private val _chapterStatus = MutableLiveData<ReadStoryApiStatus>()
    val chapterStatus: LiveData<ReadStoryApiStatus> = _chapterStatus

    private val _checkLikeStatus = MutableLiveData<ReadStoryApiStatus>()
    val checkLikeStatus: LiveData<ReadStoryApiStatus> = _checkLikeStatus

    private val _likeResponse = MutableLiveData<CheckLikeResponse>()
    val likeResponse: LiveData<CheckLikeResponse> = _likeResponse

    private val _update = MutableLiveData<UpdateLikesResponse>()
    val update: LiveData<UpdateLikesResponse> = _update

    private val _delete = MutableLiveData<DeleteLikeResponse>()
    val delete: LiveData<DeleteLikeResponse> = _delete

    private val _chapterResponse = MutableLiveData<PostChapterResponse>()
    val chapterResponse: LiveData<PostChapterResponse> = _chapterResponse

    init {
        _status.value = ReadStoryApiStatus.LOADING
    }

    fun getChapterById() {
        viewModelScope.launch {
            _status.value = ReadStoryApiStatus.LOADING
            try {
                _chapterId.value?.let {
                    ApiClient.retrofitService.getChapter(it).let { response ->
                        Log.d("ReadStoryViewModel", response.toString())
                        _chapter.value = response.chapter
                        if (response.chapter != null) {
                            _status.value = ReadStoryApiStatus.DONE
                        } else {
                            _status.value = ReadStoryApiStatus.ERROR
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("ReadStoryViewModel", e.toString())
                _status.value = ReadStoryApiStatus.ERROR
            }
        }
    }

    fun setChapterId(id: Int) {
        _chapterId.value = id
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
            _status.value = ReadStoryApiStatus.DONE
        }
    }

    fun checkLikes(uid: Int,chapterid:Int) {
        viewModelScope.launch {
            _checkLikeStatus.value = ReadStoryApiStatus.LOADING
            try {
                _likeResponse.value = ApiClient.retrofitService.checkLike(uid,chapterid)
                _checkLikeStatus.value = ReadStoryApiStatus.DONE
            } catch (e: Exception) {
                Log.e("ReadStoryViewModel", e.toString())
                _checkLikeStatus.value = ReadStoryApiStatus.ERROR
            }
        }
    }

    fun updateLikes(uid:Int , chapterId:Int){
        _checkLikeStatus.value = ReadStoryApiStatus.LOADING
        viewModelScope.launch {
            try {
                _update.value = ApiClient.retrofitService.updateLike(uid,chapterId)
                _checkLikeStatus.value = ReadStoryApiStatus.DONE
            } catch (e: Exception) {
                Log.e("ReadStoryViewModel", e.toString())
                _checkLikeStatus.value = ReadStoryApiStatus.ERROR
            }
        }
    }

    fun deleteLikes(uid:Int, chapterId : Int){
        _checkLikeStatus.value = ReadStoryApiStatus.LOADING
        viewModelScope.launch {
            try {
                _delete.value = ApiClient.retrofitService.deleteLike(uid,chapterId)
                _checkLikeStatus.value = ReadStoryApiStatus.DONE
            } catch (e: Exception) {
                Log.e("ReadStoryViewModel", e.toString())
                _checkLikeStatus.value = ReadStoryApiStatus.ERROR
            }
        }
    }
}