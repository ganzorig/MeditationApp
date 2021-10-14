package com.miu.meditationapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewParent
import androidx.viewpager2.widget.ViewPager2
import com.miu.meditationapp.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    private lateinit var onboardingItemsAdapter: OnboardingItemsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        setOnboardingItems()
    }
    private fun setOnboardingItems(){
        onboardingItemsAdapter = OnboardingItemsAdapter(
            listOf(
                OnboardingItem(
                    onboardingImage = R.drawable.pic1,
                    title = "Welcome to Meditation",
                    description = "Meditation is a practice where an individual uses a technique."
                ),
                OnboardingItem(
                    onboardingImage = R.drawable.pic3,
                    title = "Let's meditate",
                    description = "Meditation is practiced in numerous religious traditions. "
                )
            )
        )
        val onboardingViewPager = findViewById<ViewPager2>(R.id.onboardingViewPager)
        onboardingViewPager.adapter = onboardingItemsAdapter
    }
}