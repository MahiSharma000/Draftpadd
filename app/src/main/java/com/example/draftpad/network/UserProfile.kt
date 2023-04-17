package com.example.draftpad.network

import com.squareup.moshi.Json

data class UserProfile(
    val id: Int = 1,
    @Json(name = "user_id") val user_id: Int,
    @Json(name = "username") val username: String,
    @Json(name = "first_name") val first_name: String,
    @Json(name = "last_name") val last_name: String,
    @Json(name = "about") val about: String,
    @Json(name = "profile_pic") val profile_pic: String,
    @Json(name = "book_written") val book_written: String,
    @Json(name = "followers") val followers: Int = 0,
    @Json(name = "following") val following: Int = 0,
    @Json(name = "created_at") val created_at: String,
    @Json(name = "updated_at") val updated_at: String,
    @Json(name = "is_premium") val is_premium: Boolean,
    @Json(name = "books_read") val booksRead: String,
    @Json(name = "dob") val dob: String,
    @Json(name = "phone") val phone: String,
)
