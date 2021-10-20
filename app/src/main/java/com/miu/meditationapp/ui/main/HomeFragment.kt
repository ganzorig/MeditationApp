package com.miu.meditationapp.ui.main

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.miu.meditationapp.BreathActivity
import com.miu.meditationapp.MainActivity
import com.miu.meditationapp.MeditationActivity
import com.miu.meditationapp.OnboardingActivity
import com.miu.meditationapp.R
import com.miu.meditationapp.helper.NotificationReceiver
import kotlinx.android.synthetic.main.fragment_home.view.*
import java.util.*

class HomeFragment : Fragment() {
    lateinit var currentUser: FirebaseUser

    private lateinit var alarmManager: AlarmManager
    private val alarmPendingIntent by lazy {
        val intent = Intent(context, NotificationReceiver::class.java)
        PendingIntent.getBroadcast(context, 0, intent, 0)
    }

    private val HOUR_TO_SHOW_PUSH = 22
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        currentUser = FirebaseAuth.getInstance().currentUser!!

        alarmManager = context?.getSystemService(ALARM_SERVICE) as AlarmManager

        view.button.setOnClickListener {
            startActivity(Intent(context, MeditationActivity::class.java))
        }

        view.breathe.setOnClickListener {
            startActivity(Intent(context, BreathActivity::class.java))
        }

        view.remind.setOnClickListener {
//            var calendar: Calendar = Calendar.getInstance()
//
//            calendar.set(Calendar.HOUR_OF_DAY, 15)
//            calendar.set(Calendar.MINUTE, 34)
//            calendar.set(Calendar.SECOND, 15)
//
//            var intent = Intent(context, NotificationReceiver::class.java)
//            intent.action = "MY_NOTIFICATION_MESSAGE";
//
//            var pendingIntent: PendingIntent = PendingIntent.getBroadcast(this.context, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT)
//
//            var alarmManager: AlarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)


            //schedulePushNotifications()
            Toast.makeText(context, "Set alarm", Toast.LENGTH_SHORT).show()
        }

        return view
    }
    fun schedulePushNotifications() {
        val calendar = GregorianCalendar.getInstance().apply {
            if (get(Calendar.HOUR_OF_DAY) >= HOUR_TO_SHOW_PUSH) {
                add(Calendar.DAY_OF_MONTH, 1)
            }

            set(Calendar.HOUR_OF_DAY, 17)
            set(Calendar.MINUTE, 54)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)

        }

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            alarmPendingIntent
        )
    }
}
