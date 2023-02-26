package com.example.draftpad.network

data class Subscriptions(
    val id: String? = null,
    val user_id: String,
    val amount: Double,
    val duration: Int,
    val transaction_id: String,
    val created_at: String,
    val updated_at: String
)
