package com.example.draftpad.ui.profile

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
import com.example.draftpad.network.*
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.File
import java.time.LocalDateTime

enum class AuthorApiStatus { LOADING, ERROR, DONE }
class AuthorProfileViewModel : ViewModel() {
    private val _status = MutableLiveData<AuthorApiStatus>()
    val status: LiveData<AuthorApiStatus> = _status

    val _author = MutableLiveData<UserProfile?>()
    val author: LiveData<UserProfile?> = _author

    val _books = MutableLiveData<List<Book>>()
    val books: LiveData<List<Book>> = _books

    val _blockStatus = MutableLiveData<AuthorApiStatus>()
    val blockStatus: LiveData<AuthorApiStatus> = _blockStatus

    private val _userId = MutableLiveData<Int>()
    val userId: LiveData<Int> = _userId

    private val _response = MutableLiveData<AddFollowerResponse>()
    val response: LiveData<AddFollowerResponse> = _response

    private val _unfollowResponse = MutableLiveData<UnfollowResponse>()
    val unfollowResponse: LiveData<UnfollowResponse> = _unfollowResponse

    private val _followers = MutableLiveData<List<UserProfile>?>()
    val followers: MutableLiveData<List<UserProfile>?> = _followers

    private val _followerStatus = MutableLiveData<AuthorApiStatus>()
    val followerStatus: LiveData<AuthorApiStatus> = _followerStatus

    private val _checkFollow = MutableLiveData<CheckFollowResponse>()
    val checkFollow: LiveData<CheckFollowResponse> = _checkFollow

    private val _fresponse = MutableLiveData<UserDataResponse>()
    val fresponse: LiveData<UserDataResponse> = _fresponse

    private var _downloadUri = MutableLiveData<Uri?>()
    val downloadUri: MutableLiveData<Uri?> = _downloadUri

    private val _blockList = MutableLiveData<List<UserProfile>>()
    val blockList: LiveData<List<UserProfile>> = _blockList

    init {
        _status.value = AuthorApiStatus.LOADING
    }

    fun getAuthorId(uid: Int) {
        viewModelScope.launch {
            _status.value = AuthorApiStatus.LOADING
            try {
                ApiClient.retrofitService.getProfile(uid).let { response ->
                    _author.value = response.author
                    _status.value = AuthorApiStatus.DONE
                }

            } catch (e: Exception) {
                _status.value = AuthorApiStatus.ERROR
                _author.value = null
            }
        }
    }


    fun followAuthor(follower: Follower) {
        viewModelScope.launch {
            _status.value = AuthorApiStatus.LOADING
            _response.value = ApiClient.retrofitService.follow(
                follower.user_id,
                follower.follower_id
            )
        }
    }

    fun postFollow(
        userId: String,
        followerId: String
    ) {
        val currDateTime = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDateTime.now()
        } else {
            System.currentTimeMillis()
        }
        val follower = Follower(
            user_id = userId,
            follower_id = followerId,
            created_at = currDateTime.toString(),
            updated_at = currDateTime.toString()
        )
        followAuthor(follower)
    }

    fun updateProfile(
        context: Context,
        id: String,
        firstName: String,
        lastName: String,
        dob: String,
        about: String,
        phone: String,
        follower: Int,
    ) {
        try {
            val file = getFileFromUri(context, downloadUri.value!!)
            viewModelScope.launch {
                _status.value = AuthorApiStatus.LOADING

                _fresponse.value = ApiClient.retrofitService.createProfile(
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
                _status.value = AuthorApiStatus.DONE

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @SuppressLint("Recycle")
    private fun getFileFromUri(context: Context, uri: Uri): String? {
        val bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
        val file = File(context.cacheDir, "profile_pic")
        file.createNewFile()
        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 0, bos)
        val bitmapData = bos.toByteArray()
        return Base64.encodeToString(bitmapData, Base64.DEFAULT)

    }

    fun getFollower(uid: Int) {
        viewModelScope.launch {
            _followerStatus.value = AuthorApiStatus.LOADING
            try {
                ApiClient.retrofitService.getFollowers(uid).let { response ->
                    _followers.value = response.followers
                    _followerStatus.value = AuthorApiStatus.DONE
                }

            } catch (e: Exception) {
                _followerStatus.value = AuthorApiStatus.ERROR
                _followers.value = null
            }
        }
    }

    fun checkfollow(uid: Int, followerId: Int) {
        viewModelScope.launch {
            _followerStatus.value = AuthorApiStatus.LOADING
            try {
                ApiClient.retrofitService.checkFollower(uid, followerId).let { response ->
                    _checkFollow.value = response
                    _followerStatus.value = AuthorApiStatus.DONE
                }

            } catch (e: Exception) {
                _followerStatus.value = AuthorApiStatus.ERROR
                Log.e("Follower", "$e")
            }
        }
    }

    fun unfollowAuthor(userId: Int, followerId: Int) {
        viewModelScope.launch {
            _status.value = AuthorApiStatus.LOADING
            _unfollowResponse.value = ApiClient.retrofitService.unfollow(
                userId,
                followerId
            )
        }
    }

    fun blockAuthor(userId: Int, blockedId: Int) {
        viewModelScope.launch {
            _blockStatus.value = AuthorApiStatus.LOADING
            try {
                ApiClient.retrofitService.block(userId, blockedId).let { response ->
                    _blockStatus.value = AuthorApiStatus.DONE
                }
            } catch (e: Exception) {
                _blockStatus.value = AuthorApiStatus.ERROR
            }
        }
    }

    fun getBlocked(userId: Int) {
        viewModelScope.launch {
            _status.value = AuthorApiStatus.LOADING
            try {
                ApiClient.retrofitService.getBlocked(userId).let { response ->
                    _blockList.value = response.blocked
                    _status.value = AuthorApiStatus.DONE
                }
            } catch (e: Exception) {
                _status.value = AuthorApiStatus.ERROR
            }
        }
    }

    fun getBooks(uid: Int) {
        viewModelScope.launch {
            try {
                ApiClient.retrofitService.getBooksOfAuthor(uid).let { response ->
                    _books.value = response.books
                    _status.value = AuthorApiStatus.DONE
                }
            } catch (e: Exception) {
                _status.value = AuthorApiStatus.ERROR
            }
        }
    }
}