package com.miu.meditationapp.ui.main

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.auth.FirebaseUser
import com.miu.meditationapp.BreathActivity
import com.miu.meditationapp.MeditationActivity
import com.miu.meditationapp.R
import com.miu.meditationapp.helper.NotificationReceiver
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.report.view.*
import java.util.*

class HomeFragment : Fragment() {
    lateinit var currentUser: FirebaseUser
    private lateinit var alarmManager: AlarmManager
    private lateinit var calendar: Calendar
    private lateinit var picker: MaterialTimePicker
    private lateinit var database : DatabaseReference

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        calendar = Calendar.getInstance()
        createNotificationChannel()

        currentUser = FirebaseAuth.getInstance().currentUser!!
        val uid = FirebaseAuth.getInstance().uid

        database = FirebaseDatabase.getInstance().getReference("users")
        database.child(uid!!).get().addOnSuccessListener {
            if (it.exists()) {
                textView3.text = it.child("firstname").value.toString()
            } else {
                Toast.makeText(context, "User doesn't exist.", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(context, "Fail!!! User doesn't exist.", Toast.LENGTH_SHORT).show()
        }

        currentUser = FirebaseAuth.getInstance().currentUser!!
        alarmManager = context?.getSystemService(ALARM_SERVICE) as AlarmManager

        view.button.setOnClickListener {
            startActivity(Intent(context, MeditationActivity::class.java))
        }

        view.breathe.setOnClickListener {
            startActivity(Intent(context, BreathActivity::class.java))
        }

        view.statusBar.progress = 45

        view.remind.setOnClickListener {

            picker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(12)
                .setMinute(0)
                .setTitleText("Select Reminder time")
                .build()

            activity?.let { it1 -> picker.show(it1.supportFragmentManager, "GENO") }

            picker.addOnPositiveButtonClickListener {
                cancelAlarm()
                calendar.set(Calendar.HOUR_OF_DAY, picker.hour)
                calendar.set(Calendar.MINUTE, picker.minute)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)

                var intent = Intent(context, NotificationReceiver::class.java)

                var pendingIntent: PendingIntent = PendingIntent.getBroadcast(this.context, 200, intent, 0)

                var alarmManager: AlarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)
                Toast.makeText(context, "Successfully set reminder at: ${calendar.get(Calendar.HOUR_OF_DAY).toString()}:${calendar.get(Calendar.MINUTE).toString()} every day.", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    fun createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var name: CharSequence = "MORNING Meditation Channel"
            var description: String = "Channel for morning meditation remender"

            var importance: Int = NotificationManager.IMPORTANCE_DEFAULT
            var channel: NotificationChannel = NotificationChannel("MORNING", name, importance)
            channel.description = description

            var notificationManager: NotificationManager? = context?.getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(channel)
        }
    }

    fun cancelAlarm() {
        var intent = Intent(context, MeditationActivity::class.java)
        var pendingInteng = PendingIntent.getBroadcast(context, 200, intent, 0)

        if(alarmManager == null) {
            alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        }

        alarmManager.cancel(pendingInteng)
    }
}
