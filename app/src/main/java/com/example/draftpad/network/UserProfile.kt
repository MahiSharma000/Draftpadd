package com.example.draftpad.network

data class UserProfile(
    val id:String? = null,
    val first_name :String,
    val last_name :String,
    val about:String,
    val profile_pic:String,
    val book_written:String,
    val followers :Int,
    val following:Int,
    val created_at:String,
    val updated_at:String,
    val dob:String,
    val phone:String
)
