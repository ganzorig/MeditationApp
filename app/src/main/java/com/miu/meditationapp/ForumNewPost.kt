package com.miu.meditationapp

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_forum_new_post.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import java.time.LocalDateTime
import java.util.*

class ForumNewPost : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forum_new_post)
        btn_addpost.setOnClickListener() {
            if (edit_post.text != null) {
                saveUserToFirebaseDatabase()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun saveUserToFirebaseDatabase() {
        val postid = UUID.randomUUID()
        val ref = FirebaseDatabase.getInstance().getReference("/posts/$postid")
        val uid = FirebaseAuth.getInstance().uid
        var currdate = LocalDateTime.now()
        val post = PostHistory(uid.toString(), currdate.toString(), edit_post.text.toString())

        ref.setValue(post)
            .addOnSuccessListener {
                Log.d(LoginAddUser.TAG, "Finally we saved the user to Firebase Database")
            }
            .addOnFailureListener {
                Log.d(LoginAddUser.TAG, "Failed to set value to database: ${it.message}")
            }
    }
}

class PostHistory(val uid:String, val posteddate:String, val postbody: String)