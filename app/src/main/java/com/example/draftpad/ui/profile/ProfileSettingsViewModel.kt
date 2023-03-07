package com.example.draftpad.ui.profile

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.draftpad.network.ApiClient
import com.example.draftpad.network.UserDataResponse
import com.example.draftpad.network.UserProfile
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File


enum class ProfileApiStatus { LOADING, ERROR, DONE, NONE }

class ProfileSettingsViewModel : ViewModel() {

    private val _status = MutableLiveData<ProfileApiStatus>()
    val status: LiveData<ProfileApiStatus> = _status

    private val _response = MutableLiveData<UserDataResponse>()
    val response: LiveData<UserDataResponse> = _response

    private var _isImgSelected = MutableLiveData<Boolean>()
    val isImgSelected: LiveData<Boolean> = _isImgSelected

    private var _downloadUri = MutableLiveData<Uri?>()
    val downloadUri: MutableLiveData<Uri?> = _downloadUri


    init {
        _status.value = ProfileApiStatus.NONE
        _isImgSelected.value = false
        _downloadUri.value = null
    }

    private fun getUserProfile(userProfile: UserProfile) {
        viewModelScope.launch {
//            _status.value = ProfileApiStatus.LOADING
//            try {
//                _response.value = ApiClient.retrofitService.createProfile(
//                    userProfile.user_id,
//                    userProfile.first_name,
//                    userProfile.last_name,
//                    userProfile.about,
//                    userProfile.profile_pic,
//                    userProfile.book_written,
//                    userProfile.followers,
//                    userProfile.following,
//                    userProfile.created_at,
//                    userProfile.updated_at,
//                    userProfile.booksRead,
//                    userProfile.dob,
//                    userProfile.phone
//                )
//
//                _status.value = ProfileApiStatus.DONE
//            } catch (e: Exception) {
//                _status.value = ProfileApiStatus.ERROR
//                _response.value = UserDataResponse("Error", "")
//            }
        }
    }

    fun getUserProfileData(
        user_id: String?,
        first_name: String?,
        last_name: String?,
        about: String?,
        book_written: String?,
        followers: Int?,
        following: Int?,
        created_at: String?,
        updated_at: String?,
        booksRead: String?,
        dob: String?,
        phone: String?,
        profile_pic: String?,
    ) {
        val userProfile = UserProfile(
            id = null,
            user_id = user_id!!,
            first_name = first_name!!,
            last_name = last_name!!,
            about = about!!,
            profile_pic = profile_pic!!,
            book_written = book_written!!,
            followers = followers!!,
            following = following!!,
            created_at = created_at!!,
            updated_at = updated_at!!,
            booksRead = booksRead!!,
            dob = dob!!,
            phone = phone!!
        )
        getUserProfile(userProfile)

    }

    fun createOrUpdateProfile(
        context: Context,
        id: String,
        firstName: String,
        lastName: String,
        dob: String,
        about: String,
    ) {
        try {
            val file = getFileFromUri(context, downloadUri.value!!)
            val requestFile = file?.asRequestBody("image/*".toMediaTypeOrNull())
            val body = requestFile?.let {
                MultipartBody.Part.createFormData(
                    "profile_pic",
                    file.name,
                    it
                )
            }
            viewModelScope.launch {
                _status.value = ProfileApiStatus.LOADING

                _response.value = ApiClient.retrofitService.createProfile(
                    user_id = id,
                    first_name = firstName,
                    last_name = lastName,
                    about = about,
                    book_written = 0,
                    followers = 0,
                    following = 0,
                    bookRead = 0,
                    dob = dob,
                    phone = "",
                    profile_pic = body
                )
                _status.value = ProfileApiStatus.DONE

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    fun getFileFromUri(context: Context, uri: Uri): File? {
        val filePathColumn = arrayOf(android.provider.MediaStore.Images.Media.DATA)
        val cursor = context.contentResolver.query(uri, filePathColumn, null, null, null)
        cursor?.moveToFirst()
        val columnIndex = cursor?.getColumnIndex(filePathColumn[0])
        val filePath = columnIndex?.let { cursor.getString(it) }
        cursor?.close()
        return filePath?.let { File(it) }
    }

    fun setImageUri(uri: Uri?, selected: Boolean) {
        _downloadUri.value = uri
        _isImgSelected.value = selected
    }
}
