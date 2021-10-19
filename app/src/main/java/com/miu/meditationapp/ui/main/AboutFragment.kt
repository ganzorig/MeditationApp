package com.miu.meditationapp.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.miu.meditationapp.R
import kotlinx.android.synthetic.main.fragment_about.view.*
import java.util.*


class AboutFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_about, container, false)

        val calendar = Calendar.getInstance()

        view.cal.date = calendar.timeInMillis

        return view
    }
}
