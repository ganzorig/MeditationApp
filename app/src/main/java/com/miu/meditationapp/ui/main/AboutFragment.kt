package com.miu.meditationapp.ui.main

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.miu.meditationapp.LoginActivity
import com.miu.meditationapp.R
import kotlinx.android.synthetic.main.fragment_about.view.*
import java.net.URL
import java.util.*
import android.widget.Toast
import com.google.firebase.database.DatabaseError
import com.squareup.picasso.Picasso
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener

class AboutFragment : Fragment() {
    private lateinit var viewModel: AboutViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_about, container, false)
        viewModel = ViewModelProvider(this).get(AboutViewModel::class.java)

        viewModel.getFirstName()
        viewModel.liveFirstName
            .observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                view.name.text = "Hi ${it.toString()}!"
            })
        viewModel.getProfilePictureField()

        viewModel.liveProfilePicture
            .observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                var url = URL(it.toString())
                val firebaseDatabase = FirebaseDatabase.getInstance()
                val databaseReference: DatabaseReference = firebaseDatabase.getReference()
                val getImage: DatabaseReference = databaseReference.child("profileImageUrl")
                getImage.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val link = dataSnapshot.getValue(String::class.java)
                        Picasso.get().load(it.toString()).into(view.avatar)
                    }
                    override fun onCancelled(databaseError: DatabaseError) {
                        Toast.makeText(context, "Error Loading Image", Toast.LENGTH_SHORT).show()
                    }
                })
            })
        val calendar = Calendar.getInstance()
        view.cal.date = calendar.timeInMillis

        view.logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut();
            startActivity(Intent(context, LoginActivity::class.java))

            var preferences: SharedPreferences? = context?.getSharedPreferences("ONBOARD", Context.MODE_PRIVATE)
            preferences?.edit()?.remove("ISCOMPLETE")?.commit( );
            activity?.finish()
        }
        return view
    }
}
