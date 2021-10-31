package com.miu.meditationapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.miu.meditationapp.R

class LearnFragment : Fragment(), MyAdapter.ItemClickListener {
    private lateinit var viewModel: LearnViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        var view = inflater.inflate(R.layout.fragment_learn, container, false)

        viewModel = ViewModelProvider(this).get(LearnViewModel::class.java)

        var rv = view.findViewById<RecyclerView>(R.id.recyclerView1)
        rv.layoutManager = LinearLayoutManager(context)
        val adapter = MyAdapter(viewModel.getData(), this)
        rv.adapter = adapter

        return view
    }

    override fun onItemClick (position: Int){
        val intent = Intent(context, VideosView::class.java)
        intent.putExtra("videos", viewModel.getData()[position])
        startActivity(intent)
    }
}