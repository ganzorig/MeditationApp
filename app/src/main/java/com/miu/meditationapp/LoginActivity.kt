package com.miu.meditationapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    lateinit var inputEmail: TextInputEditText
    lateinit var inputPassword: TextInputEditText
    lateinit var loginbutton:Button
    lateinit var registertv:TextView
    lateinit var loadingPB:ProgressBar
    lateinit var mAuth:FirebaseAuth
    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        inputEmail = input_email
        inputPassword = input_password

        loginbutton = findViewById(R.id.btn_login)
        registertv = findViewById(R.id.registerTV)
        mAuth = FirebaseAuth.getInstance()
        loadingPB = findViewById(R.id.progressbar)

        preferences = getSharedPreferences("ONBOARD", Context.MODE_PRIVATE)

        registertv.setOnClickListener() {
            startActivity(Intent(this, LoginAddUser::class.java))
        }
        loginbutton.setOnClickListener() {
            val email:String = inputEmail.text.toString()
            val pwd:String = inputPassword.text.toString()
            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pwd)) {
                Toast.makeText(this, "Please enter your credentials", Toast.LENGTH_SHORT).show()

            } else {
                mAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        loadingPB.visibility = View.GONE
                        Toast.makeText(this, "Login Successful..", Toast.LENGTH_SHORT).show()
                        startMainActivity()
                    } else {
                        loadingPB.visibility = View.GONE
                        Toast.makeText(this, "Email or Password is wrong. Please try again.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun startMainActivity() {
        if (isSeenOnbaord()) {
            startActivity(Intent(this, MainActivity::class.java))
        } else {
            startActivity(Intent(this, OnboardingActivity::class.java))
        }
        finish()
    }

    override fun onStart() {
        super.onStart()
        var user: FirebaseUser? = mAuth.currentUser
        if (user != null) {
            startMainActivity()
        }
    }

    fun isSeenOnbaord(): Boolean {
        return preferences.getBoolean("ISCOMPLETE", false)
    }
}