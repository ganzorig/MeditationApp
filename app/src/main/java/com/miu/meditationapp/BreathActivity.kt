package com.miu.meditationapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_breath.*
import kotlinx.android.synthetic.main.activity_breath.close
import kotlinx.android.synthetic.main.activity_breath.indicator
import kotlinx.android.synthetic.main.activity_breath.start
import java.util.concurrent.TimeUnit

class BreathActivity : AppCompatActivity() {
    private lateinit var textIndicator: TextView
    private lateinit var timer: CountDownTimer
    var isRunning = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_breath)

        textIndicator = indicator
        timer = createTimer()

        start.setOnClickListener {
            toggle()
        }

        close.setOnClickListener {
            finish()
        }
    }

    private fun toggle() {
        if(isRunning) {
            stopExercise()
            start.text = "Start"
        } else {
            breathe.playAnimation()
            start.text = getString(R.string.str_end)
            timer.start()
        }
    }

    private fun createTimer(): CountDownTimer {
        return object: CountDownTimer(3 * 60000, 1000) {
            var sec = 0L
            var min = 3L
            override fun onTick(ms: Long) {
                isRunning = true
                min = TimeUnit.MILLISECONDS.toMinutes(ms) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(ms))
                sec = TimeUnit.MILLISECONDS.toSeconds(ms) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(ms))

                textIndicator.text = "$min:$sec"
            }

            override fun onFinish() {
                stopExercise()
            }
        }
    }

    fun stopExercise() {
        breathe.pauseAnimation()
        isRunning = false
        timer.cancel()
    }

    override fun onStop() {
        super.onStop()
        timer.cancel()
    }
}