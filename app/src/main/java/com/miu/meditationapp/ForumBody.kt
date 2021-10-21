package com.miu.meditationapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.Sampler
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import kotlinx.android.synthetic.main.activity_forum_body.*
import kotlinx.android.synthetic.main.activity_forum_new_post.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import org.w3c.dom.Comment

class ForumBody : AppCompatActivity() {
    //val recyclerView: RecyclerView? = null
    private var adapter: RecyclerAdapter? = null
    var items : MutableList<PostHistory> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forum_body)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerV)
        recyclerView.layoutManager = LinearLayoutManager(this)
        items = arrayListOf()
        button4.setOnClickListener() {
            startActivity(Intent(this, ForumNewPost::class.java))
        }



        Log.e("TAG", "hope");

        val ref = FirebaseDatabase.getInstance().getReference("posts")
        ref.keepSynced(true)
        val postListener : ValueEventListener = object:ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                items.clear()
                //courtsOrig.clear();
                //firebase-н courts доторх өгөгдлүүдийг бүгдийг нь авж байна...
                for (courtSnapshot in dataSnapshot.children) {
                    val uid = courtSnapshot.child("uid").value as String?
                    val posteddate = courtSnapshot.child("posteddate").value as String?
                    val postbody = courtSnapshot.child("postbody").value as String?
                    val newFood = PostHistory(uid!!, posteddate!!, postbody!!)
                    //courts гэсэн лист рүүгээ дээрхүүдийг оруулж байна
                    items.add(newFood)
                    Log.e("Spe","-------------------$postbody")

                }
                adapter = RecyclerAdapter(applicationContext,items)

                adapter!!.notifyDataSetChanged() //refresh

                recyclerView?.adapter = adapter
                //recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }
        ref.addValueEventListener(postListener)
    }

}