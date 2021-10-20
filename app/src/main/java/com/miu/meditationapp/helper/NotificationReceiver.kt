package com.miu.meditationapp.helper

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.miu.meditationapp.MainActivity
import com.miu.meditationapp.MeditationActivity

class NotificationReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        var notificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        var repeatingIntent = Intent(context, MeditationActivity::class.java)
        repeatingIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP

        var pendingIntent: PendingIntent = PendingIntent.getActivity(context, 100, repeatingIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        var builder: NotificationCompat.Builder = NotificationCompat.Builder(context, "MORNING")
            .setContentIntent(pendingIntent)
            .setSmallIcon(android.R.drawable.arrow_down_float)
            .setContentTitle("Content title")
            .setContentText("Content text")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        notificationManager.notify(100,builder.build())
//        with(NotificationManagerCompat.from(context)) {
//            // notificationId is a unique int for each notification that you must define
//            notificationManager.notify(100,builder.build())
//        }
    }

}