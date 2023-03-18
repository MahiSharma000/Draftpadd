package com.example.draftpad.ui.write

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.draftpad.network.ApiClient
import com.example.draftpad.network.Book
import com.example.draftpad.network.PostBookResponse
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.File
import java.time.LocalDateTime


enum class NewStoryApiStatus { LOADING, ERROR, DONE, NONE }

class NewStoryViewModel : ViewModel() {

    private val _status = MutableLiveData<NewStoryApiStatus>()
    val status: MutableLiveData<NewStoryApiStatus> = _status

    private val _response = MutableLiveData<PostBookResponse>()
    val response: MutableLiveData<PostBookResponse> = _response

    private var _isImgSelected = MutableLiveData<Boolean>()
    val isImgSelected: LiveData<Boolean> = _isImgSelected

    private var _downloadUri = MutableLiveData<Uri?>()
    val downloadUri: MutableLiveData<Uri?> = _downloadUri


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
        title: String,
        description: String,
        userId: Int
    ) {
        try {
            val file = getFileFromUri(context, downloadUri.value!!)
            val currDateTime = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                LocalDateTime.now()
            } else {
                System.currentTimeMillis()
            }
            val book = Book(
                id = 1,
                title = title!!,
                description = description!!,
                cover = getFileFromUri(context, downloadUri.value!!),
                category_id = 1,
                user_id = 1,
                username = "",
                chapters = 0,
                status = 0,
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