package com.example.draftpad.ui.write

import android.content.Context
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.draftpad.network.*
import kotlinx.coroutines.launch
import java.time.LocalDateTime

enum class WriteApiStatus { LOADING, ERROR, DONE, NONE }

class WriteViewModel : ViewModel() {

    private val _status = MutableLiveData<WriteApiStatus>()
    val status: MutableLiveData<WriteApiStatus> = _status

    private val _response = MutableLiveData<PostChapterResponse>()
    val response: MutableLiveData<PostChapterResponse> = _response

    private val _bookResponse = MutableLiveData<PostBookResponse>()
    val bookResponse: MutableLiveData<PostBookResponse> = _bookResponse

    init {
        _status.value = WriteApiStatus.NONE
    }

    fun createNewChapter(
        bookId: Int,
        chapterTitle: String,
        chapterContent: String,
        status: Int,
        categoryid: Int
    ) {
        Log.d("Chapter", "createNewChapter: $bookId")
        val chapter = Chapter(
            id = 1,
            book_Id = bookId,
            title = chapterTitle,
            content = chapterContent,
            category_id = categoryid,
            status = status,
            total_comments = 0,
            total_likes = 0,
            user_Id = 1,
            book_title = "",
            book_views = 0,
        )
        postChapter(chapter)
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

    fun postBook(book: Book) {
        viewModelScope.launch {
            _status.value = WriteApiStatus.LOADING
            _bookResponse.value = ApiClient.retrofitService.createBook(
                title = book.title,
                description = book.description,
                category_id = book.category_id,
                user_id = book.user_id,
                status = book.status,
                views = book.views,
                lang = book.lang,
                created_at = book.created_at,
                cover = book.cover.toString()
            )
            _status.value = WriteApiStatus.DONE
        }
    }

    fun createnewBook(
        context: Context,
        title: String,
        description: String,
        status: Int,
        userId: Int,
        categoryId: Int,
    ) {
        try {
            val currDateTime = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                LocalDateTime.now()
            } else {
                System.currentTimeMillis()
            }
            val book = Book(
                id = 1,
                title = title!!,
                description = description!!,
                cover = "",
                category_id = categoryId,
                user_id = userId,
                username = "",
                chapters = 0,
                status = status,
                views = 0,
                lang = "English",
                created_at = currDateTime.toString(),
                updated_at = currDateTime.toString(),
            )
            postBook(book)
        } catch (e: Exception) {
            Log.d("Error", "createnewBook: ${e.message}")
        }
    }
}


