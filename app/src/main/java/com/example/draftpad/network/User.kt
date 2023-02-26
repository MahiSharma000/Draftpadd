package com.example.draftpad.network

import com.squareup.moshi.Json

data class User(
    val id: String? = null,
    val userName: String,
    val userEmail: String,
    val userPassword: String,
    val lastSeen: String,
)
