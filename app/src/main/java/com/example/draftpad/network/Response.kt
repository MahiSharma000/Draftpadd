package com.example.draftpad.network

data class RegisterResponse(
    val status: String,
    val msg: String
)

data class LoginResponse(
    val status: String,
    val username: String,
    val email: String,
    val id: String
)

data class LogoutResponse(
    val status: String
)

data class CategoryAllResponse(
    val status: String,
    val categories: List<Category>
)

data class BooksByCategoryResponse(
    val status: String,
    val books: List<Book>
)

data class CreateBookResponse(
    val status: String,
    val msg: String

)

data class BooksAllResponse(
    val status: String,
    val books: List<Book>
)