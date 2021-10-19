package com.miu.meditationapp

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_login_add_user.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class LoginAddUser  : AppCompatActivity() {

    companion object {
        val TAG = "RegisterActivity"
    }

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
                         uploadImageToFirebaseStorage()
                         startActivity(Intent(this, LoginActivity::class.java))
                         finish()
                     } else {
                         loadingPB.visibility = View.GONE
                         Toast.makeText(this, "Fail to create user...", Toast.LENGTH_SHORT)
                     }
                }
            }
        }
        selectphoto_button_register.setOnClickListener() {
            Log.d("LoginAddUserActivity", "Try to show photo selector")
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }
    }
    var selectedPhotoUri: Uri? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            // proceed and check what the selected image was....
            Log.d(TAG, "Photo was selected")

            selectedPhotoUri = data.data

            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedPhotoUri)

            selectphoto_imageview_register.setImageBitmap(bitmap)

            selectphoto_button_register.alpha = 0f

//      val bitmapDrawable = BitmapDrawable(bitmap)
//      selectphoto_button_register.setBackgroundDrawable(bitmapDrawable)
        }
    }

    private fun performRegister() {
        val email = emailEdt.text.toString()
        val password = passwordEdt.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter text in email/pw", Toast.LENGTH_SHORT).show()
            return
        }

        Log.d(TAG, "Attempting to create user with email: $email")

        // Firebase Authentication to create a user with email and password
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (!it.isSuccessful) return@addOnCompleteListener

                // else if successful
                Log.d(TAG, "Successfully created user with uid: ${it.result?.user?.uid}")

                uploadImageToFirebaseStorage()
            }
            .addOnFailureListener{
                Log.d(TAG, "Failed to create user: ${it.message}")
                Toast.makeText(this, "Failed to create user: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun uploadImageToFirebaseStorage() {
        if (selectedPhotoUri == null) return

        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")

        ref.putFile(selectedPhotoUri!!)
            .addOnSuccessListener {
                Log.d(TAG, "Successfully uploaded image: ${it.metadata?.path}")

                ref.downloadUrl.addOnSuccessListener {
                    Log.d(TAG, "File Location: $it")

                    saveUserToFirebaseDatabase(it.toString())
                }
            }
            .addOnFailureListener {
                Log.d(TAG, "Failed to upload image to storage: ${it.message}")
            }
    }

    private fun saveUserToFirebaseDatabase(profileImageUrl: String) {
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")

        val user = User(uid, firstNameEdt.text.toString(), lastNameEdt.text.toString(), emailEdt.text.toString(), profileImageUrl)

        ref.setValue(user)
            .addOnSuccessListener {
                Log.d(TAG, "Finally we saved the user to Firebase Database")
            }
            .addOnFailureListener {
                Log.d(TAG, "Failed to set value to database: ${it.message}")
            }
    }

}

class User(val uid: String, val firstname: String, val lastname: String, val username: String, val profileImageUrl: String)