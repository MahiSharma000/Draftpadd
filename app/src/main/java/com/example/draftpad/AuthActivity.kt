package com.example.draftpad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.draftpad.auth.SharedPreferenceManager

class AuthActivity : AppCompatActivity() {

    private val sharedPrefManager: SharedPreferenceManager by lazy {
        SharedPreferenceManager(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

    }

    override fun onStart() {
        super.onStart()
        val logged = Utils(this).getUser().isLogged()
        Log.d("AuthActivity", "Logged: $logged")
        if (logged) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }


}