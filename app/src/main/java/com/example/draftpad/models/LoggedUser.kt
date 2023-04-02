package com.example.draftpad.models

class LoggedUser(
    val username: String,
    val email: String,
    val id: String
){
    fun isLogged(): Boolean {
        return username != "" && email != "" && id != ""
    }
}
