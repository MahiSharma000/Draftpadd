package com.example.draftpad.network

data class Follower(
    val id : String? = null,
    val user_id : String,
    val follower_id : String,
    val created_at : String,
    val updated_at : String
)
