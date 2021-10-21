package com.miu.meditationapp.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.miu.meditationapp.*
import kotlinx.android.synthetic.main.fragment_forum.view.*

class ForumFragment : Fragment() {
    private var adapter: RecyclerAdapter? = null
    var items : MutableList<PostHistory> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_forum, container, false)

        view.recyclerV.layoutManager = LinearLayoutManager(context)
        items = arrayListOf()

        val ref = FirebaseDatabase.getInstance().getReference("posts")
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
                adapter!!.notifyDataSetChanged()
                view.recyclerV?.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }
        ref.addValueEventListener(postListener)

        view.btn_new_post.setOnClickListener() {
            startActivity(Intent(context, ForumNewPost::class.java))
        }
        return view
    }
}
