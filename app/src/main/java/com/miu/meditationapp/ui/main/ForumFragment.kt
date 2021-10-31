package com.miu.meditationapp.ui.main

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.miu.meditationapp.*
import com.miu.meditationapp.models.PostHistory
import kotlinx.android.synthetic.main.fragment_forum.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ForumFragment : Fragment() {
    private var adapter: RecyclerAdapter? = null
    var items : MutableList<PostHistory> = ArrayList()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_forum, container, false)

        view.recyclerV.layoutManager = LinearLayoutManager(context)
        items = arrayListOf()

        val ref = FirebaseDatabase.getInstance().getReference("posts").orderByChild("posteddate")

        ref.keepSynced(true)
        val postListener : ValueEventListener = object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                items.clear()
                for (courtSnapshot in dataSnapshot.children) {
                    val uid = courtSnapshot.child("uid").value as String?


                    val posteddate = courtSnapshot.child("posteddate").value as String?
                    val postbody = courtSnapshot.child("postbody").value as String?
                    val newFood = PostHistory(uid!!, posteddate!!, postbody!!)
                    items.add(newFood)
                }
                adapter = activity?.let { RecyclerAdapter(it.applicationContext, items) }
                adapter?.notifyDataSetChanged()
                view.recyclerV?.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }
        ref.addValueEventListener(postListener)

        view.send_button.setOnClickListener() {
            if (view.edittext_chat.text.isNotEmpty()) {
                saveUserToFirebaseDatabase(view)
            }
        }
        return view
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun saveUserToFirebaseDatabase(view: View) {
        val postid = UUID.randomUUID()
        val ref = FirebaseDatabase.getInstance().getReference("/posts/$postid")
        val uid = FirebaseAuth.getInstance().uid
        val sdf = SimpleDateFormat("MM/dd/yyyy hh:mm:ss")
        var currdate = sdf.format(Date())
        val post = PostHistory(uid.toString(), currdate.toString(), view.edittext_chat.text.toString())

        ref.setValue(post)
            .addOnSuccessListener {
                Log.d(LoginAddUser.TAG, "Finally we saved the user to Firebase Database")
            }
            .addOnFailureListener {
                Log.d(LoginAddUser.TAG, "Failed to set value to database: ${it.message}")
            }
        view.edittext_chat.text.clear()
    }
}
