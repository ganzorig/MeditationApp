package com.miu.meditationapp.ui.main

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel: ViewModel() {
    var times: Int = 0;
    lateinit var sharedPreferences: SharedPreferences
    val MED_COUNT = "meditation_count"
    val MED_MINUTES = "meditation_minutes"
    val BREATHE_COUNT = "breathe_count"
    val BREATHE_MINUTES = "breathe_minutes"
    var test = MutableLiveData<String>()

    fun init(context: Context, prefName: String) {
        sharedPreferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
    }

    fun getMeditationCount(): Int {
        return sharedPreferences.getInt(MED_COUNT, 0)
    }

    fun updateMeditationCount() {
        sharedPreferences.edit().putInt(MED_COUNT, getMeditationCount() + 1).apply()
    }

    fun getMeditationMin(): Int {
        return sharedPreferences.getInt(MED_MINUTES, 0)
    }

    fun updateMeditationMinutes(minutes: Int) {
        sharedPreferences.edit().putInt(MED_MINUTES, getMeditationMin() + minutes).apply()
    }

    fun getBreatheCount(): Int {
        return sharedPreferences.getInt(BREATHE_COUNT, 0)
    }

    fun updateBreatheCount() {
        sharedPreferences.edit().putInt(BREATHE_COUNT, getBreatheCount() + 1).apply()
    }

    fun getBreatheMin(): Int {
        return sharedPreferences.getInt(BREATHE_MINUTES, 0)
    }

    fun updateBreatheMin(minutes: Int) {
        sharedPreferences.edit().putInt(BREATHE_MINUTES, getBreatheMin() + minutes).apply()
    }
}