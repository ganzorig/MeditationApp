package com.miu.meditationapp.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.miu.meditationapp.ForumBody
import com.miu.meditationapp.R
import com.miu.meditationapp.messages.LatestMessagesActivity
import com.miu.meditationapp.messages.NewMessageActivity
import kotlinx.android.synthetic.main.fragment_forum.*
import kotlinx.android.synthetic.main.fragment_forum.view.*
import kotlinx.android.synthetic.main.activity_forum_new_post.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*


class ForumFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        val view = inflater.inflate(R.layout.fragment_forum, container, false)
        view.forum1.setOnClickListener {
            startActivity(Intent(activity, ForumBody::class.java))
        }
        return view

    }



}
