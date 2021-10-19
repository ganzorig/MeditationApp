package com.miu.meditationapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth


class LoginAddUser  : AppCompatActivity() {
    lateinit var firstNameEdt: EditText
    lateinit var lastNameEdt: EditText
    lateinit var emailEdt: EditText
    lateinit var passwordEdt: EditText
    lateinit var cpasswordEdt: EditText
    lateinit var loginTV: TextView
    lateinit var createuserBtn: Button
    lateinit var loadingPB: ProgressBar
    lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_add_user)
        firstNameEdt = findViewById(R.id.ptxt_firstname)
        lastNameEdt = findViewById(R.id.ptxt_lastname)
        emailEdt = findViewById(R.id.ptxt_email)
        passwordEdt = findViewById(R.id.ptxtpassword)
        cpasswordEdt = findViewById(R.id.ptxt_cpassword)
        createuserBtn = findViewById<Button>(R.id.btn_createuser)
        loadingPB = findViewById(R.id.progressbar)
        loginTV = findViewById(R.id.idTVLogin)
        mAuth = FirebaseAuth.getInstance()
        loginTV.setOnClickListener() {
            var i = Intent(this, LoginActivity::class.java)
            startActivity(i)
        }
        createuserBtn.setOnClickListener() {
            loadingPB.visibility = View.VISIBLE
            val fname:String = firstNameEdt.text.toString()
            val lname:String = lastNameEdt.text.toString()
            val email:String = emailEdt.text.toString()
            val pwd:String = passwordEdt.text.toString()
            val cpwd:String = cpasswordEdt.text.toString()

            if(!pwd.equals(cpwd)) {
                Toast.makeText(this, "Please check both password", Toast.LENGTH_SHORT)
            } else if(TextUtils.isEmpty(fname) && TextUtils.isEmpty(lname) && TextUtils.isEmpty(email) && TextUtils.isEmpty(pwd) && TextUtils.isEmpty(cpwd)) {
                Toast.makeText(this, "Please add fully information..", Toast.LENGTH_SHORT)
            } else {
                mAuth.createUserWithEmailAndPassword(email,pwd).addOnCompleteListener(this) { task ->
                     if(task.isSuccessful) {
                         loadingPB.visibility = View.GONE
                         Toast.makeText(this, "User Created...", Toast.LENGTH_SHORT)
                         startActivity(Intent(this, LoginActivity::class.java))
                         finish()
                     } else {
                         loadingPB.visibility = View.GONE
                         Toast.makeText(this, "Fail to create user...", Toast.LENGTH_SHORT)
                     }
                }
            }
        }
    }
}