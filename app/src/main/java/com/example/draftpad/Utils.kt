package com.example.draftpad

import android.content.Context
import android.content.SharedPreferences
import com.example.draftpad.models.LoggedUser

class Utils(val context: Context) {
    val prefs: SharedPreferences =
        context.getSharedPreferences("draftpad", Context.MODE_PRIVATE)

    fun getUser(): LoggedUser {
        val username = prefs.getString("username", "")
        val email = prefs.getString("email", "")
        val id = prefs.getString("id", "")
        return LoggedUser(username!!, email!!, id!!)
    }

    fun saveLoggedUser(user: LoggedUser) {
        val editor = prefs.edit()
        editor.putString("username", user.username)
        editor.putString("email", user.email)
        editor.putString("id", user.id)
        editor.apply()
    }

    fun logout() {
        val editor = prefs.edit()
        editor.remove("username")
        editor.remove("email")
        editor.remove("id")
        editor.apply()
    }
}