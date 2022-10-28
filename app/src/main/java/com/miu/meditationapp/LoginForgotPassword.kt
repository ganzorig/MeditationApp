package com.miu.meditationapp

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth


class LoginForgotPassword : AppCompatActivity() {
    companion object {
        val TAG = "ForgotActivity"
    }
    lateinit var forgotbutton:Button
    lateinit var forgot_txt:TextView
    lateinit var loginTV: TextView
    private var auth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_forgot_password)
        auth = FirebaseAuth.getInstance()
        forgot_txt = findViewById(R.id.forgottxt_email)
        forgotbutton = findViewById(R.id.btn_forgotPassword)

        forgotbutton.setOnClickListener() {
            val email: String = forgot_txt.getText().toString().trim()
            print(email)
            FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener {task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Email sent successfully", Toast.LENGTH_LONG).show()
                        finish()
                    } else {
                        Toast.makeText(this, task.exception!!.message.toString(), Toast.LENGTH_LONG).show()
                    }

                }
        }

        loginTV = findViewById(R.id.idTVLogin)

        loginTV.setOnClickListener() {
            var i = Intent(this, LoginActivity::class.java)
            startActivity(i)

        }
    }
}