package com.example.draftpad.ui.profile

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.draftpad.Utils
import com.example.draftpad.network.ApiClient
import com.example.draftpad.network.UserDataResponse
import com.example.draftpad.network.UserProfile
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.File



enum class ProfileApiStatus { LOADING, ERROR, DONE, NONE }

class ProfileSettingsViewModel : ViewModel() {

    private val _status = MutableLiveData<ProfileApiStatus>()
    val status: LiveData<ProfileApiStatus> = _status

    private val _getstatus = MutableLiveData<ProfileApiStatus>()
    val getstatus: LiveData<ProfileApiStatus> = _getstatus

    private val _response = MutableLiveData<UserDataResponse>()
    val response: LiveData<UserDataResponse> = _response

    private val _user = MutableLiveData<UserProfile?>()
    val user: LiveData<UserProfile?> = _user

    private var _isImgSelected = MutableLiveData<Boolean>()
    val isImgSelected: LiveData<Boolean> = _isImgSelected

    private var _downloadUri = MutableLiveData<Uri?>()
    val downloadUri: MutableLiveData<Uri?> = _downloadUri


    init {
        _status.value = ProfileApiStatus.NONE
        _isImgSelected.value = false
        _downloadUri.value = null
    }

    fun createOrUpdateProfile(
        context: Context,
        id: String,
        firstName: String,
        lastName: String,
        dob: String,
        about: String,
        phone:String,
        follower: Int,
    ) {
        try {
            val file = getFileFromUri(context, downloadUri.value!!)
            viewModelScope.launch {
                _status.value = ProfileApiStatus.LOADING

                _response.value = ApiClient.retrofitService.createProfile(
                    user_id = id,
                    first_name = firstName,
                    last_name = lastName,
                    about = about,
                    book_written = 0,
                    followers = follower,
                    following = 0,
                    bookRead = 0,
                    is_premium = false,
                    dob = dob,
                    phone = phone,
                    profile_pic = file!!
                )
                _status.value = ProfileApiStatus.DONE

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
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

    fun getUser(uid: Int) {
        viewModelScope.launch {
            _getstatus.value = ProfileApiStatus.LOADING
            try {
                ApiClient.retrofitService.getProfile(uid).let { response ->
                    Log.d("ProfileSettingsViewModel", "getUserId: $response")
                    _user.value = response.author
                    _getstatus.value = ProfileApiStatus.DONE
                }

            } catch (e: Exception) {
                _getstatus.value = ProfileApiStatus.ERROR
                _user.value = null
                Log.e("ProfileSettingViewModel", "getUserId: $e")
            }
        }
    }
}
