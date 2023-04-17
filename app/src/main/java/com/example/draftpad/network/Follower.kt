package com.example.draftpad.network

import com.squareup.moshi.Json

data class Follower(
    val id: String? = null,
    @Json(name = "user_id")
    val user_id: String,
    @Json(name = "follower_id")
    val follower_id: String,
    @Json(name = "created_at")
    val created_at: String,
    @Json(name = "updated_at")
    val updated_at: String
)
