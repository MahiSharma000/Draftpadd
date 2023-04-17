package com.example.draftpad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class AuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

    }
    override fun onStart() {
        super.onStart()
        val logged = Utils(this).getUser().isLogged()
        if (logged) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}