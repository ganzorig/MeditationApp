package com.miu.meditationapp.ui.main

import androidx.lifecycle.ViewModel
import com.miu.meditationapp.R

class LearnViewModel : ViewModel() {
    var videos = ArrayList<Videos>()

    fun fillData() {
        val video1: Videos = Videos("https://stoptrafficking.mn/wp-content/videos/tm01.mp4","How To Meditate For Beginners (Animated)","How To Meditate For Beginners! In this video, I'm going to tell you, where to meditate, how to meditate, how to stop thinking, how long to meditate for, even how long before you start seeing the benefits. ", "05:36", R.drawable.meditation1)
        val video2: Videos = Videos("https://stoptrafficking.mn/wp-content/videos/tm02.mp4","Alan Watts - Guided Meditation (Awakening The Mind)","This is a 14-minute Alan Watts guided meditation video discussing his method of establishing a meditative state and reaching self-awareness.\n", "14.45", R.drawable.meditation2)
        val video3: Videos = Videos("https://stoptrafficking.mn/wp-content/videos/tm03.mp4","Meditation For Children (Calming activity)", "Meditation is a powerful practice. Our children today live in a world so full of constant stimulation and entertainment. Learning to sit still and breathe can help kids to calm themselves when they feel stressed or anxious.","06:13", R.drawable.meditation3)
        val video4: Videos = Videos("https://stoptrafficking.mn/wp-content/videos/tm04.mp4","Meditation: A Beginner's Guide", "Are you new to meditation, and interested in finding out how to start a practice? We'll walk you through the basics! \n","02:00", R.drawable.meditation4)
        val video5: Videos = Videos("https://stoptrafficking.mn/wp-content/videos/tm05.mp4","The Art of Meditation (Animated video)", "Meditation is probably a bit more complicated than most people think.\n" +
                "\n" +
                "When we meditate we watch our thoughts, while focusing our attention on a certain anchor that keeps us in the present, for example, the breath. While watching the breath we observe how our thoughts come and go, along with our feelings and emotions. And when we get dragged into them, we bring back our attention to our anchor.","06:37", R.drawable.meditation5)
        val video6: Videos = Videos("https://stoptrafficking.mn/wp-content/videos/tm06.mp4","Animation movie on Transcendental Meditation", "Idea, Script and voiceover: Moti Shefi\n" +
                "Animation: Ravid Sandlerman\n" +
                "Mr Jones: Roee Berger\n" +
                "Original Music: Ofra Avni and Yitzhak Yona\n" +
                "Sound effects : freesfx.co.uk","07:46", R.drawable.meditation6)
        videos.add(video1)
        videos.add(video2)
        videos.add(video3)
        videos.add(video4)
        videos.add(video5)
        videos.add(video6)
    }

    fun getData(): ArrayList<Videos> {
        fillData()
        return videos
    }

}