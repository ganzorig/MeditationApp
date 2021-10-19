package com.miu.meditationapp

import android.animation.Animator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_breath.*
import kotlinx.android.synthetic.main.activity_breath.close
import kotlinx.android.synthetic.main.activity_breath.indicator
import kotlinx.android.synthetic.main.activity_breath.start
import kotlinx.android.synthetic.main.activity_meditation.*
import java.util.*
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
            breathe.pauseAnimation()
            timer.cancel()
            start.isClickable = false
            start.text = "Canceled"
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
                println("finish")
                breathe.clearAnimation()
                isRunning = false
            }
        }
    }

    override fun onStop() {
        super.onStop()
        timer.cancel()
    }
}