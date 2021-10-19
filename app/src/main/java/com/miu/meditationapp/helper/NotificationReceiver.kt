package com.miu.meditationapp.helper

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.miu.meditationapp.MainActivity

class NotificationReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        var notificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        var repeatingIntent = Intent(context, MainActivity::class.java)
        repeatingIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP

        var pendingIntent: PendingIntent = PendingIntent.getActivity(context, 100, repeatingIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        var builder: NotificationCompat.Builder = NotificationCompat.Builder(context)
            .setContentIntent(pendingIntent)
            .setSmallIcon(android.R.drawable.arrow_down_float)
            .setContentTitle("Content title")
            .setContentText("Content text")
            .setAutoCancel(true)

        notificationManager.notify(100, builder.build())
    }

}