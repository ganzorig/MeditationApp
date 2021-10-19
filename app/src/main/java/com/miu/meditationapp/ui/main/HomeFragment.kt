package com.miu.meditationapp.ui.main

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.miu.meditationapp.BreathActivity
import com.miu.meditationapp.MainActivity
import com.miu.meditationapp.MeditationActivity
import com.miu.meditationapp.OnboardingActivity
import com.miu.meditationapp.R
import com.miu.meditationapp.helper.NotificationReceiver
import kotlinx.android.synthetic.main.fragment_home.view.*
import java.util.*

class HomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)

        view.button.setOnClickListener {
            startActivity(Intent(context, MeditationActivity::class.java))
        }

        view.breathe.setOnClickListener {
            startActivity(Intent(context, BreathActivity::class.java))
        }


        view.remind.setOnClickListener {
            var calendar: Calendar = Calendar.getInstance()

            calendar.set(Calendar.HOUR_OF_DAY, 3)
            calendar.set(Calendar.MINUTE, 13)
            calendar.set(Calendar.SECOND, 0)

            var intent = Intent(context, NotificationReceiver::class.java)

            var pendingIntent: PendingIntent = PendingIntent.getBroadcast(context, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT)

            var alarmManager: AlarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)

            Toast.makeText(context, "Set alarm", Toast.LENGTH_SHORT).show()
        }

//        view.onboard.setOnClickListener {
//            startActivity(Intent(context, OnboardingActivity::class.java))
//        }


        return view
    }
}
