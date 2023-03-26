package com.example.draftpad.network

import com.squareup.moshi.Json

data class Report(
    val id: String? = null,
    @Json(name = "user_id")
    val user_id: String,
    @Json(name = "book_id")
    val book_id: String,
    @Json(name = "report_type")
    val report_type: String,
    @Json(name = "report_reason")
    val report_reason: String
)
