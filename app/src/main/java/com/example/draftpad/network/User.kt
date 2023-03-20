package com.example.draftpad.network

import com.squareup.moshi.Json

data class User(
    val id: String?,
    @Json(name = "username") val userName: String,
    @Json(name = "email") val email: String,
    @Json(name = "password") val password: String,
    @Json(name = "last_seen") val lastSeen: String,
)
