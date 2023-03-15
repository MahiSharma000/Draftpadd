package com.example.draftpad.ui.write

import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.draftpad.network.ApiClient
import com.example.draftpad.network.Chapter
import com.example.draftpad.network.PostChapterResponse
import kotlinx.coroutines.launch
import java.time.LocalDateTime

enum class WriteApiStatus { LOADING, ERROR, DONE, NONE }

class WriteViewModel : ViewModel() {

    private val _status = MutableLiveData<WriteApiStatus>()
    val status: MutableLiveData<WriteApiStatus> = _status

    private val _response = MutableLiveData<PostChapterResponse>()
    val response: MutableLiveData<PostChapterResponse> = _response

    init {
        _status.value = WriteApiStatus.NONE
    }

    fun createNewChapter(bookId: Int, chapterTitle: String, chapterContent: String) {
        try {
            val currDateTime = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                LocalDateTime.now()
            } else {
                System.currentTimeMillis()
            }
            val chapter=Chapter(
                id = 1,
                book_Id = bookId!!,
                title = chapterTitle!!,
                content = chapterContent!!,
                created_at = currDateTime.toString(),
                updated_at = currDateTime.toString(),
                category_id = 1,
                status = 0,
                total_comments = 0,
                total_likes = 0,
                user_Id = 1,
            )
            postChapter(chapter)
        }catch (e:Exception){
            Log.d("Error", "createnewChapter: ${e.message}")
        }
    }

    private fun postChapter(chapter: Chapter) {
        viewModelScope.launch {
            _status.value = WriteApiStatus.LOADING
            _response.value = ApiClient.retrofitService.createChapter(
                title = chapter.title,
                book_id = chapter.book_Id,
                content = chapter.content,
                category_id = chapter.category_id,
                user_id = chapter.user_Id,
                status = chapter.status,
                created_at = chapter.created_at,
                updated_at = chapter.updated_at,
                total_comments = chapter.total_comments,
                total_likes = chapter.total_likes,
            )
            _status.value = WriteApiStatus.DONE
        }
    }

}