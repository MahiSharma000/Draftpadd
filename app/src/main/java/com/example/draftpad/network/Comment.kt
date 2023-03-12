package com.example.draftpad.network

import com.squareup.moshi.Json

data class Comment(
    val id: String? = null,
    @Json(name = "content") val content: String,
    @Json(name = "user_id") val user_id: Int,
    @Json(name = "chapter_id") val chapter_id: Int,
    @Json(name = "created_at") val created_at: String,
    @Json(name = "updated_at") val updated_at: String
)
