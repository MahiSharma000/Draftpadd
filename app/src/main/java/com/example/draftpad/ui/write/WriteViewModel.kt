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

    fun createNewChapter(bookId: Int, chapterTitle: String, chapterContent: String,status : Int) {
            val chapter=Chapter(
                id = 1,
                book_Id = bookId!!,
                title = chapterTitle!!,
                content = chapterContent!!,
                category_id = 1,
                status = status,
                total_comments = 0,
                total_likes = 0,
                user_Id = 1,
                book_title = "",
                book_views = 0,
            )
            postChapter(chapter)
//        }catch (e:Exception){
//            Log.d("Error", "createnewChapter: ${e.message}")
//        }
    }


    private fun postChapter(chapter: Chapter) {
        Log.d("Chapter", "postChapter: ${chapter.title}")
        viewModelScope.launch {
            _status.value = WriteApiStatus.LOADING
            _response.value = ApiClient.retrofitService.createChapter(
                title = chapter.title,
                book_id = chapter.book_Id,
                content = chapter.content,
                category_id = chapter.category_id,
                user_id = chapter.user_Id,
                status = chapter.status,
                total_comments = chapter.total_comments,
                total_likes = chapter.total_likes,
            )
            _status.value = WriteApiStatus.DONE
        }
    }


}