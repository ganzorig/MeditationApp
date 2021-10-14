package com.miu.meditationapp

import androidx.appcompat.app.AppCompatActivity
import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.miu.meditationapp.databinding.ActivityMeditationBinding
import kotlinx.android.synthetic.main.activity_meditation.*
import java.util.*
import java.util.concurrent.TimeUnit


class MeditationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMeditationBinding
    private lateinit var mediaPlayer: MediaPlayer
    private var isFullscreen: Boolean = false
    private lateinit var tts: TextToSpeech

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMeditationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        isFullscreen = true

        val textIndicator = indicator
        mediaPlayer = MediaPlayer.create(applicationContext, R.raw.back_sound)

        val timer = object: CountDownTimer(1200000, 1000) {
            var min = 20L
            var sec = 0L
            override fun onTick(ms: Long) {
                min = TimeUnit.MILLISECONDS.toMinutes(ms) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(ms))
                sec = TimeUnit.MILLISECONDS.toSeconds(ms) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(ms))

                if(min == 19L && sec == 55L) {
                    tts = TextToSpeech(applicationContext, TextToSpeech.OnInitListener {
                        if(it == TextToSpeech.SUCCESS) {
                            tts.language = Locale.US
                            tts.setSpeechRate(0.8F)
                            tts.speak("Close your eyes and sit comfortably", TextToSpeech.QUEUE_ADD, null)
                        }
                    })
                }
                textIndicator.text = "$min:$sec"
            }

            override fun onFinish() {
                println("finish")
            }
        }

        close.setOnClickListener {
            showdialog(this)
        }

        start.setOnClickListener {
            timer.start()

            mediaPlayer.isLooping = true
            mediaPlayer.start()
            start.visibility = View.GONE
        }

        sound.setOnClickListener {
            if(mediaPlayer.isPlaying) {
                sound.setImageResource(R.drawable.sound)
                mediaPlayer.pause()
            } else {
                sound.setImageResource(R.drawable.sound_no)
                mediaPlayer.start()
            }

        }
    }


    override fun onStop() {
        super.onStop()
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
            mediaPlayer.release()
        }
    }

    override fun onBackPressed() {
        showdialog(this)

    }

    private fun showdialog(context: Context){
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)

        builder.setMessage("Do you want to close this application ?").setCancelable(true)
        builder.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
            
            finish()
        })

        builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })
        val alert = builder.create()
        alert.setTitle("Are you sure")
        alert.show()
    }

}