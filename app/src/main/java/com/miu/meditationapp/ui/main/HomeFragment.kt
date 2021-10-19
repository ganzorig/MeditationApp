package com.miu.meditationapp.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.miu.meditationapp.BreathActivity
import com.miu.meditationapp.MeditationActivity
import com.miu.meditationapp.OnboardingActivity
import com.miu.meditationapp.R
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)

        view.button.setOnClickListener {
            startActivity(Intent(context, MeditationActivity::class.java))
        }

        view.breathe.setOnClickListener {
            startActivity(Intent(context, BreathActivity::class.java))
        }

//        view.onboard.setOnClickListener {
//            startActivity(Intent(context, OnboardingActivity::class.java))
//        }

        return view
    }
}
