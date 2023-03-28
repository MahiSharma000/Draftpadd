package com.example.draftpad.ui.read

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.draftpad.network.*
import com.example.draftpad.ui.write.WriteApiStatus
import kotlinx.coroutines.launch
import java.time.LocalDateTime

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

    private val _bookstatus = MutableLiveData<ReadStoryApiStatus>()
    val bookstatus: MutableLiveData<ReadStoryApiStatus> = _bookstatus

    private val _bookResponse = MutableLiveData<PostBookResponse>()
    val bookResponse: MutableLiveData<PostBookResponse> = _bookResponse

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

    fun postupdateBook(book: Book) {
        viewModelScope.launch {
            _bookstatus.value = ReadStoryApiStatus.LOADING
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
            _status.value = ReadStoryApiStatus.DONE
        }
    }

    fun updateBook(
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
            postupdateBook(book)
        } catch (e: Exception) {
            Log.d("Error", "createnewBook: ${e.message}")
        }
    }
}