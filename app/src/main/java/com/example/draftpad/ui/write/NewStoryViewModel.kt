package com.example.draftpad.ui.write

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
//import androidx.compose.foundation.gestures.ModifierLocalScrollableContainerProvider.value
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.draftpad.network.*
import com.example.draftpad.ui.profile.ProfileApiStatus
import com.example.draftpad.ui.read.ReadApiStatus
import com.example.draftpad.ui.read.ReadStoryApiStatus
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.File
import java.time.LocalDateTime


enum class NewStoryApiStatus { LOADING, ERROR, DONE, NONE }

class NewStoryViewModel : ViewModel() {

    private val _status = MutableLiveData<NewStoryApiStatus>()
    val status: MutableLiveData<NewStoryApiStatus> = _status

    private val _deleteStatus = MutableLiveData<NewStoryApiStatus>()
    val deleteStatus: MutableLiveData<NewStoryApiStatus> = _deleteStatus

    private val _response = MutableLiveData<PostBookResponse>()
    val response: MutableLiveData<PostBookResponse> = _response

    private val _bookId = MutableLiveData<Int>()
    val bookId: MutableLiveData<Int> = _bookId

    private val _book = MutableLiveData<Book>()
    val book: MutableLiveData<Book> = _book

    private val _chapterId = MutableLiveData<Int>()
    val chapterId: LiveData<Int> = _chapterId

    private val _chapter = MutableLiveData<Chapter>()
    val chapter: LiveData<Chapter> = _chapter

    private val _chapterStatus = MutableLiveData<ReadStoryApiStatus>()
    val chapterStatus: LiveData<ReadStoryApiStatus> = _chapterStatus

    private val _chapterResponse = MutableLiveData<PostChapterResponse>()
    val chapterResponse: LiveData<PostChapterResponse> = _chapterResponse

    private val _getStatus = MutableLiveData<ReadApiStatus>()
    val getStatus: MutableLiveData<ReadApiStatus> = _getStatus

    private var _isImgSelected = MutableLiveData<Boolean>()
    val isImgSelected: LiveData<Boolean> = _isImgSelected

    private var _downloadUri = MutableLiveData<Uri?>()
    val downloadUri: MutableLiveData<Uri?> = _downloadUri

    private var _isEdited = MutableLiveData<Boolean>(false)
    val isEdited: LiveData<Boolean> = _isEdited

    private var _catName = MutableLiveData<String>()
    val catName: LiveData<String> = _catName

    private val _catId = MutableLiveData<Int>()
    val catId: LiveData<Int> = _catId

    private val _categoryResponse = MutableLiveData<CategoryResponse>()
    val categoryResponse: LiveData<CategoryResponse> = _categoryResponse

    init {
        _status.value = NewStoryApiStatus.NONE
        _isImgSelected.value = false
        _downloadUri.value = null
    }

    private fun convertToString(file: File): String {
        Log.d("File", "convertToString: ${file.absolutePath}")
        val bytes = file.readBytes()
        return Base64.encodeToString(bytes, Base64.DEFAULT)
    }

    @SuppressLint("Recycle")
    private fun getFileFromUri(context: Context, uri: Uri): String? {
        // get the file path from the URI using the content resolver
        val bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
        val file = File(context.cacheDir, "profile_pic")
        file.createNewFile()
        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 0, bos)
        val bitmapData = bos.toByteArray()
        return Base64.encodeToString(bitmapData, Base64.DEFAULT)

    }

    fun setImageUri(uri: Uri?, selected: Boolean) {
        _downloadUri.value = uri
        _isImgSelected.value = selected
    }

    fun postBook(book: Book) {
        viewModelScope.launch {
            _status.value = NewStoryApiStatus.LOADING
            _response.value = ApiClient.retrofitService.createBook(
                id = book.id,
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
            _status.value = NewStoryApiStatus.DONE
        }
    }

    fun createnewBook(
        context: Context,
        id: Int,
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
            val cover = if (_isImgSelected.value == true) {
                _downloadUri.value?.let { getFileFromUri(context, it) }
            } else {
                _book.value?.cover
            }
            val book = Book(
                id = id,
                title = title!!,
                description = description!!,
                cover = cover,
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

    fun getSelectedBook() {
        Log.d("NewStoryViewModel", _bookId.value.toString())
        viewModelScope.launch {
            _getStatus.value = ReadApiStatus.LOADING
            try {
                _bookId.value?.let {
                    ApiClient.retrofitService.getBook(it).let { response ->
                        _book.value = response.book
                        _getStatus.value = ReadApiStatus.DONE
                        Log.d("ReadViewModel", _book.value.toString())
                    }
                }
            } catch (e: Exception) {
                Log.e("ReadViewModel", e.toString())
                _getStatus.value = ReadApiStatus.ERROR

            }
        }
    }


    fun setBookId(id: Int) {
        _bookId.value = id
        Log.d("ReadViewModel", _bookId.value.toString())
    }

    fun updateChapter(
        id: Int,
        bookId: Int,
        chapterTitle: String,
        chapterContent: String,
        status: Int,
        categoryid: Int,
        likes: Int,
        comments: Int,
        uid: Int
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
            _chapterStatus.value = ReadStoryApiStatus.DONE
        }
    }

    fun getChapterById() {
        viewModelScope.launch {
            _chapterStatus.value = ReadStoryApiStatus.LOADING
            try {
                _chapterId.value?.let {
                    ApiClient.retrofitService.getChapter(it).let { response ->
                        Log.d("ReadStoryViewModel", response.toString())
                        _chapter.value = response.chapter
                        if (response.chapter != null) {
                            _chapterStatus.value = ReadStoryApiStatus.DONE
                        } else {
                            _chapterStatus.value = ReadStoryApiStatus.ERROR
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("ReadStoryViewModel", e.toString())
                _chapterStatus.value = ReadStoryApiStatus.ERROR
            }
        }

    }

    fun deletebook(bookid:Int){
        viewModelScope.launch {
            _deleteStatus.value = NewStoryApiStatus.LOADING
            try {
                bookid.let {
                    ApiClient.retrofitService.deleteBook(it).let { response ->
                        Log.d("DeleteBook", response.toString())
                        _deleteStatus.value = NewStoryApiStatus.DONE
                    }
                }
            } catch (e: Exception) {
                Log.e("DeleteBook", e.toString())
                _deleteStatus.value = NewStoryApiStatus.ERROR
            }
        }
    }

    fun deletechapter(chapter:Int){
        viewModelScope.launch {
            _deleteStatus.value = NewStoryApiStatus.LOADING
            try {
                chapter.let {
                    ApiClient.retrofitService.deleteChapter(it).let { response ->
                        Log.d("DeleteChapter", response.toString())
                        _deleteStatus.value = NewStoryApiStatus.DONE
                    }
                }
            } catch (e: Exception) {
                Log.e("DeleteChapter", e.toString())
                _deleteStatus.value = NewStoryApiStatus.ERROR
            }
        }
    }


    fun setChapterId(id: Int) {
        _chapterId.value = id
    }
}