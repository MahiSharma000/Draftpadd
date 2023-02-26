package com.example.draftpad.network

data class Comment(
    val id: String? = null,
    val content: String,
    val user_id: String,
    val chapter_id: String,
    val created_at: String,
    val updated_at: String
)
