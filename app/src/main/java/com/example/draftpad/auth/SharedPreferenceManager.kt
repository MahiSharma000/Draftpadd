package com.example.draftpad.auth

import android.content.Context

class SharedPreferenceManager(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)

    fun setLoggedIn(isLoggedInt: Boolean){
        sharedPreferences.edit().putBoolean("isLoggedIn", isLoggedInt).apply()
    }

    fun isLoggedIn(): Boolean{
        return sharedPreferences.getBoolean("isLoggedIn", false)
    }
}