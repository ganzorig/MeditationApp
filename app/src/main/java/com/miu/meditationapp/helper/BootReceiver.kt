package com.miu.meditationapp.helper

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class BootReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent != null && intent.action == "android.intent.action.BOOT_COMPLETED") {

        }
    }
}