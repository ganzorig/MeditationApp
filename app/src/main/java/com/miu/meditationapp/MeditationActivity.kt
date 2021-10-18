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
import android.widget.ArrayAdapter
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
    private lateinit var  values : Array<String>

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMeditationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        isFullscreen = true

        values = resources.getStringArray(R.array.minutes);
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, values)
        spinner.adapter = adapter;

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
            showDialog(this)
        }

        start.setOnClickListener {
            timer.start()

            mediaPlayer.isLooping = true
            mediaPlayer.start()
            start.text = getString(R.string.str_end)
        }

        sound.setOnClickListener {
            if(mediaPlayer.isPlaying) {
                sound.setImageResource(R.drawable.sound_no)
                mediaPlayer.pause()
            } else {
                sound.setImageResource(R.drawable.sound)
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
        showDialog(this)
    }

    private fun showDialog(context: Context){
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)

        builder.setMessage("Do you want to stop meditating ?").setCancelable(true)
        builder.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
            finish()
        })

        builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })
        val alert = builder.create()
        alert.setTitle("Are you sure")
        alert.show()
    }

}