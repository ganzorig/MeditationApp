package com.miu.meditationapp.ui.main

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.miu.meditationapp.R
import kotlinx.android.synthetic.main.fragment_home.*

class AboutViewModel : ViewModel() {
     lateinit var database : DatabaseReference
      var photoURL : String = ""
      var firstname : String = ""
      var lastname : String = ""
      var email : String = ""

     var liveFirstName = MutableLiveData<String>()
     var liveProfilePicture = MutableLiveData<String>()

     fun getProfilePictureField () {
         val uid = FirebaseAuth.getInstance().uid
         database = FirebaseDatabase.getInstance().getReference("users")
         database.child(uid!!).get().addOnSuccessListener {
             if (it.exists()) {
                 liveProfilePicture.value = it.child("profileImageUrl").value.toString()
             } else {
                 Log.d("AboutViewModel","Failed to load Photo")
             }
         }.addOnFailureListener {
             Log.d("AboutViewModel","Failed to load Photo")
         }
     }

    fun getFirstName (): String {
        val uid = FirebaseAuth.getInstance().uid
        println("UID$uid")
        database = FirebaseDatabase.getInstance().getReference("users")
        database.child(uid!!).get().addOnSuccessListener {
            if (it.exists()) {
                firstname = it.child("firstname").value.toString()
                liveFirstName.value = firstname
                println("Thissssssssssss"+firstname)
            } else {
                Log.d("AboutViewModel","Failed to load Photo")
            }
        }.addOnFailureListener {
            Log.d("AboutViewModel","Failed to load Photo")
        }
        println("lastttttttttttt $firstname")
        return firstname
    }
    fun getLastName () {
        val uid = FirebaseAuth.getInstance().uid
        database = FirebaseDatabase.getInstance().getReference("users")
        database.child(uid!!).get().addOnSuccessListener {
            if (it.exists()) {
                lastname = it.child("lastname").value.toString()
            } else {
                Log.d("AboutViewModel","Failed to load Photo")
            }
        }.addOnFailureListener {
            Log.d("AboutViewModel","Failed to load Photo")
        }
    }
    fun getEmail () {
        val uid = FirebaseAuth.getInstance().uid
        database = FirebaseDatabase.getInstance().getReference("users")
        database.child(uid!!).get().addOnSuccessListener {
            if (it.exists()) {
                email = it.child("username").value.toString()
            } else {
                Log.d("AboutViewModel","Failed to load Photo")
            }
        }.addOnFailureListener {
            Log.d("AboutViewModel","Failed to load Photo")
        }
    }
}