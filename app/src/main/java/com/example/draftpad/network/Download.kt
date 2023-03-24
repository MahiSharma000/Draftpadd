package com.example.draftpad.network

import com.squareup.moshi.Json

data class Download(
    val id: String? = null,
    @Json(name = "book_id")
    val book_id: Int,
    @Json(name = "user_id")
    val user_id: Int,
    @Json(name = "created_at")
    val created_at: String,
    @Json(name = "updated_at")
    val updated_at: String
)
