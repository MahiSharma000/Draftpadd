package com.example.draftpad.network

data class ReadingList(
    val id: String? = null,
    val name: String,
    val user_id: String,
    val book_id: String,
    val created_at: String,
    val updated_at: String
)
