package com.example.draftpad.network

data class Chapter(
    val id: String? = null,
    val title: String,
    val book_id: String,
    val user_id: String,
    val content: String,
    val created_at: String,
    val updated_at: String,
    val views: String,
    val total_comments: String,
     val total_likes: String,
    val status: String,

)
