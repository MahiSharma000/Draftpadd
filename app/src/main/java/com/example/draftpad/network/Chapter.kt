package com.example.draftpad.network

import com.squareup.moshi.Json

data class Chapter(
    val id: Int = 1,
    @Json(name = "title") val title: String,
    @Json(name = "book_id") val book_Id: Int,
    @Json(name = "book_title") val book_title: String,
    @Json(name = "book_views") val book_views: Int,
    @Json(name = "category_id") val category_id: Int,
    @Json(name = "user_id") val user_Id: Int,
    @Json(name = "content") val content: String,
    @Json(name = "created_at") val created_at: String,
    @Json(name = "updated_at") val updated_at: String,
    @Json(name = "total_comments") val total_comments: Int,
    @Json(name = "total_likes") val total_likes: Int,
    @Json(name = "status") val status: Int,

    )
