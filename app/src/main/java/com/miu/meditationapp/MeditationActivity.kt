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
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
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
    private lateinit var values : Array<String>
    private var minutes = 20L
    private lateinit var textIndicator:TextView
    private lateinit var timer: CountDownTimer
    private var isVoiceEnabled: Boolean = true

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMeditationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        isFullscreen = true

        textIndicator = indicator

        values = resources.getStringArray(R.array.minutes);
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, values)
        spinner.adapter = adapter;

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                minutes = if(position == 0) 20L else 10L
                textIndicator.text = "$minutes:00"
                timer.cancel()
                timer = createTimer()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        mediaPlayer = MediaPlayer.create(applicationContext, R.raw.back_sound)
        timer = createTimer()

        close.setOnClickListener {
            showDialog(this)
        }

        speech.setOnClickListener {
            if(isVoiceEnabled) {
                isVoiceEnabled = false
                speech.setImageResource(R.drawable.mic_no)
            } else {
                isVoiceEnabled = true
                speech.setImageResource(R.drawable.mic)
            }
        }

        start.setOnClickListener {
            timer.start()
            spinner.isEnabled = false
            spinner.isClickable = false
            mediaPlayer.isLooping = true
            mediaPlayer.start()
            start.isClickable = false
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
        timer.cancel()
    }

    override fun onBackPressed() {
        showDialog(this)
    }

    private fun createTimer(): CountDownTimer {
        return object: CountDownTimer(minutes * 60000, 1000) {
            var sec = 0L
            override fun onTick(ms: Long) {
                minutes = TimeUnit.MILLISECONDS.toMinutes(ms) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(ms))
                sec = TimeUnit.MILLISECONDS.toSeconds(ms) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(ms))

                if((minutes == 19L || minutes == 9L) && sec == 55L) {
                    tts = TextToSpeech(applicationContext, TextToSpeech.OnInitListener {
                        if(isVoiceEnabled && it == TextToSpeech.SUCCESS) {
                            tts.language = Locale.US
                            tts.setSpeechRate(0.8F)
                            tts.speak("Close your eyes and sit comfortably", TextToSpeech.QUEUE_ADD, null)
                        }
                    })
                }

                if(minutes == 2L) {
                    tts = TextToSpeech(applicationContext, TextToSpeech.OnInitListener {
                        if(isVoiceEnabled && it == TextToSpeech.SUCCESS) {
                            tts.language = Locale.US
                            tts.setSpeechRate(0.8F)
                            tts.speak("Stop thinking mantra, take two more minutes", TextToSpeech.QUEUE_ADD, null)
                        }
                    })
                }
                textIndicator.text = "$minutes:$sec"
            }

            override fun onFinish() {
                println("finish")
            }
        }
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