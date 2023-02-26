package com.example.draftpad.network

data class Download(
    val id: String? = null,
    val book_id: String,
    val user_id: String,
    val created_at: String,
    val updated_at: String
)
