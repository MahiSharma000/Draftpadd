package com.example.draftpad.network

data class Book(
    val id: String? = null,
    val title: String,
    val user_id: String,
    val username: String,
    val category_id: String,
    val description: String,
    val cover: String? = "https://th.bing.com/th/id/OIP.VUur7KSOIcdFCTvmXKluSQHaHa?pid=ImgDet&rs=1",
    val created_at: String,
    val updated_at: String,
    val lang:String,
    val views: String,
    val chapters:Int?=0

)
