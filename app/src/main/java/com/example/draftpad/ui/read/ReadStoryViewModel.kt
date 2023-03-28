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

    private val _chapterResponse = MutableLiveData<PostChapterResponse>()
    val chapterResponse: LiveData<PostChapterResponse> = _chapterResponse

    private val _update = MutableLiveData<UpdateLikesResponse>()
    val update: LiveData<UpdateLikesResponse> = _update

    private val _delete = MutableLiveData<DeleteLikeResponse>()
    val delete: LiveData<DeleteLikeResponse> = _delete

    private val _checkLike = MutableLiveData<CheckLikeResponse>()
    val checkLike: LiveData<CheckLikeResponse> = _checkLike

    private val _likeResponse = MutableLiveData<CheckLikeResponse>()
    val likeResponse: LiveData<CheckLikeResponse> = _likeResponse

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

    fun deleteLikes(uid:Int, chapter_id:Int){
        viewModelScope.launch {
            _chapterStatus.value = ReadStoryApiStatus.LOADING
            _delete.value = ApiClient.retrofitService.deleteLike(uid, chapter_id)
            _status.value = ReadStoryApiStatus.DONE
        }
    }

    fun updateLikes(uid:Int, chapter_id:Int){
        viewModelScope.launch {
            _chapterStatus.value = ReadStoryApiStatus.LOADING
            _update.value = ApiClient.retrofitService.updateLike(uid, chapter_id)
            _status.value = ReadStoryApiStatus.DONE
        }
    }

    fun checkLikes(uid:Int, chapter_id:Int){
        viewModelScope.launch {
            _chapterStatus.value = ReadStoryApiStatus.LOADING
            _checkLike.value = ApiClient.retrofitService.checkLike(uid, chapter_id)
            _status.value = ReadStoryApiStatus.DONE
        }
    }
}