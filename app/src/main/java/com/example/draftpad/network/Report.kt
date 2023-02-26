package com.example.draftpad.network

data class Report(
    val id: String? = null,
    val user_id: String,
    val book_id: String,
    val chapter_id: String,
    val comment_id: String,
    val report_type: String,
    val report_reason: String,
    val created_at: String,
    val updated_at: String
)
