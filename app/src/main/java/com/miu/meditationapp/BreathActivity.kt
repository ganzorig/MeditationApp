package com.miu.meditationapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Visibility
import android.view.View
import kotlinx.android.synthetic.main.activity_breath.*
import kotlinx.android.synthetic.main.fragment_home.*

class BreathActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_breath)

        play.setOnClickListener {
            play.playAnimation()

            //breatheView.visibility = View.VISIBLE
            //play.visibility = View.GONE


        }

//
//        play.addAnimatorListener()
    }
}