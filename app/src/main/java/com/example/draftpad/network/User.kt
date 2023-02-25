package com.example.draftpad.network

import com.squareup.moshi.Json

data class User(
    val id: String? = null,
    @Json(name = "username") val userName: String,
    @Json(name = "email") val userEmail: String,
    @Json(name = "password") val userPassword: String,
    @Json(name = "last_seen") val lastSeen: String,
)
