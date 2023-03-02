package com.example.draftpad.network

data class Book(
    val id: String? = null,
    val title: String,
    val user_id: String,
    val category_id: String,
    val description: String,
    val cover: String,
    val created_at: String,
    val updated_at: String,
    val lang:String,
    val views: String

)
