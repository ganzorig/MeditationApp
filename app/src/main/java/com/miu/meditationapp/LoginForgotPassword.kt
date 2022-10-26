package com.miu.meditationapp

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class LoginForgotPassword : AppCompatActivity() {
    companion object {
        val TAG = "ForgotActivity"
    }
//    lateinit var forgotbutton:Button
//    lateinit var forgot_txt:TextView
    lateinit var loginTV: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_forgot_password)

        loginTV = findViewById(R.id.idTVLogin)

        loginTV.setOnClickListener() {
            var i = Intent(this, LoginActivity::class.java)
            startActivity(i)
        }
    }
}