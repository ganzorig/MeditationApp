package com.miu.meditationapp.ui.main

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.miu.meditationapp.*
import com.miu.meditationapp.R
import com.miu.meditationapp.helper.NotificationReceiver
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import java.util.*

class HomeFragment : Fragment() {

    private lateinit var database : DatabaseReference

    companion object {
        var currentUser: User? = null
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val uid = FirebaseAuth.getInstance().uid

        database = FirebaseDatabase.getInstance().getReference("users")
        database.child(uid!!).get().addOnSuccessListener {
            if (it.exists()) {
                textView3.text = it.child("firstname").value.toString()
            } else {
                Toast.makeText(context, "User doesn't exist.", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(context, "User doesn't exist.", Toast.LENGTH_SHORT).show()
        }

        view.button.setOnClickListener {
            startActivity(Intent(context, MeditationActivity::class.java))
        }

        view.breathe.setOnClickListener {
            startActivity(Intent(context, BreathActivity::class.java))
        }

        view.remind.setOnClickListener {
            var calendar: Calendar = Calendar.getInstance()

            calendar.set(Calendar.HOUR_OF_DAY, 15)
            calendar.set(Calendar.MINUTE, 34)
            calendar.set(Calendar.SECOND, 15)

            var intent = Intent(context, NotificationReceiver::class.java)
            intent.action = "MY_NOTIFICATION_MESSAGE";

            var pendingIntent: PendingIntent = PendingIntent.getBroadcast(this.context, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT)

            var alarmManager: AlarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)

            Toast.makeText(context, "Set alarm", Toast.LENGTH_SHORT).show()
        }

        return view
    }

    private fun fetchCurrentUser() {
        val uid = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")
        ref.addListenerForSingleValueEvent(object: ValueEventListener {

            override fun onDataChange(p0: DataSnapshot) {
                currentUser = p0.getValue(User::class.java)
                textView3.text = currentUser?.firstname
                Log.d("LatestMessages", "Current user ${currentUser?.profileImageUrl}")
            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })
    }
}
