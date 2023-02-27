package com.example.draftpad.network

import com.squareup.moshi.Json

data class Category(
    val id: Int,
    @Json(name = "category_name")
    val name: String,

    )
