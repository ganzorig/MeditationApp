package com.miu.meditationapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    lateinit var email:EditText
    lateinit var pwd:EditText
    lateinit var loginbutton:Button
    lateinit var registertv:TextView
    lateinit var loadingPB:ProgressBar
    lateinit var mAuth:FirebaseAuth
    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        email = findViewById(R.id.ptxt_login_email)
        pwd = findViewById(R.id.ptxt_login_password)
        loginbutton = findViewById(R.id.btn_login)
        registertv = findViewById(R.id.registerTV)
        mAuth = FirebaseAuth.getInstance()
        loadingPB = findViewById(R.id.progressbar)

        preferences = getSharedPreferences("ONBOARD", Context.MODE_PRIVATE)

        registertv.setOnClickListener() {
            startActivity(Intent(this, LoginAddUser::class.java))
        }
        loginbutton.setOnClickListener() {
            val email:String = email.text.toString()
            val pwd:String = pwd.text.toString()
            if (TextUtils.isEmpty(email) && TextUtils.isEmpty(pwd)) {
                Toast.makeText(this, "Please enter your credentials/", Toast.LENGTH_SHORT)
            } else {
                mAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        loadingPB.visibility = View.GONE
                        Toast.makeText(this, "Login Successful..", Toast.LENGTH_SHORT)
                        startMainActivity()
                    } else {
                        loadingPB.visibility = View.GONE
                        Toast.makeText(this, "Email or Password is wrong. Please try again.", Toast.LENGTH_SHORT)
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