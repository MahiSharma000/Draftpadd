package com.example.draftpad.network

import com.squareup.moshi.Json

data class Chapter(
    val id: String? = null,
    @Json(name = "title") val title: String,
    @Json(name = "book_id") val book_Id: String,
    @Json(name = "user_id") val user_Id: String,
    @Json(name = "content") val content: String,
    @Json(name = "created_at") val created_at: String,
    @Json(name = "updated_at") val updated_at: String,
    @Json(name = "views") val views: String,
    @Json(name = "total_comments") val total_comments: String,
    @Json(name = "total_likes") val total_likes: String,
    @Json(name = "status") val status: String,

    )
