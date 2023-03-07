package com.example.draftpad.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.draftpad.network.ApiClient
import com.example.draftpad.network.UserProfile
import kotlinx.coroutines.launch


enum class ProfileApiStatus { LOADING, ERROR, DONE, NONE }

class ProfileSettingsViewModel : ViewModel() {

    private val _status = MutableLiveData<ProfileApiStatus>()
    val status: LiveData<ProfileApiStatus> = _status

    private val _response = MutableLiveData<UserProfile>()
    val response: LiveData<UserProfile> = _response

    init {
        _status.value = ProfileApiStatus.NONE
    }

    private fun getUserProfile(userProfile: UserProfile) {
        viewModelScope.launch {
            _status.value = ProfileApiStatus.LOADING
            try {
                _response.value = ApiClient.retrofitService.createProfile(
                    userProfile.user_id,
                    userProfile.first_name,
                    userProfile.last_name,
                    userProfile.about,
                    userProfile.profile_pic,
                    userProfile.book_written,
                    userProfile.followers,
                    userProfile.following,
                    userProfile.created_at,
                    userProfile.updated_at,
                    userProfile.dob,
                    userProfile.phone
                )
                _status.value = ProfileApiStatus.DONE
            } catch (e: Exception) {
                _status.value = ProfileApiStatus.ERROR
               // _response.value =UserDataResponse("Error", e.message.toString())
            }
        }
    }

    fun getUserProfileData(user_id: String?,first_name:String?,last_name:String?,about:String?,
                           profile_pic:String?, book_written:String?,followers:Int?,following:Int?,created_at:String?,updated_at:String?,dob:String?,phone:String?) {
        val userProfile = UserProfile(
            id=null,
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
            dob = dob!!,
            phone = phone!!
        )
        getUserProfile(userProfile)

    }
}
