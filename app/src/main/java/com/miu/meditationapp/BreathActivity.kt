package com.miu.meditationapp

import android.animation.Animator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_breath.*

class BreathActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_breath)

        breathe.visibility = View.GONE

        play.setOnClickListener {
            play.playAnimation()
        }

        play.addAnimatorListener(object : Animator.AnimatorListener{
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                breathe.visibility = View.VISIBLE
                play.visibility = View.GONE
                Toast.makeText(applicationContext, "sad", Toast.LENGTH_SHORT).show()
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
            }
        })
    }
}