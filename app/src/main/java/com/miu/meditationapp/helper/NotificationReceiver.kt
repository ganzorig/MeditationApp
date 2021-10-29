package com.miu.meditationapp.helper

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.miu.meditationapp.MeditationActivity
import com.miu.meditationapp.R

class NotificationReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        var repeatingIntent = Intent(context, MeditationActivity::class.java)
        repeatingIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP

        var pendingIntent: PendingIntent = PendingIntent.getActivity(context, 200, repeatingIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        var builder: NotificationCompat.Builder = NotificationCompat.Builder(context, "MORNING")
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.about)
            .setContentTitle("Ding!!! ")
            .setContentText("It is time to Meditate! Click here to start meditating.")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        var notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(200, builder.build())
    }
}