package com.example.draftpad.network

import com.squareup.moshi.Json

data class Book(
    val id: Int =1,
    @Json(name = "title") val title: String,
    @Json(name = "category_id") val category_id: Int,
    @Json(name = "user_id") val user_id: Int,
    @Json(name = "username") val username: String,
    @Json(name = "cover") val cover: String? = "https://th.bing.com/th/id/OIP.VUur7KSOIcdFCTvmXKluSQHaHa?pid=ImgDet&rs=1",
    @Json(name = "status") val status: Int,
    @Json(name = "chapters") val chapters: Int,
    @Json(name = "description") val description: String,
    @Json(name = "created_at") val created_at: String,
    @Json(name = "updated_at") val updated_at: String,
    @Json(name = "lang") val lang: String,
    @Json(name = "views") val views: Int = 0,
)
