package com.miu.meditationapp


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import com.miu.meditationapp.ui.main.AboutFragment
import com.miu.meditationapp.ui.main.HomeFragment
import com.miu.meditationapp.ui.main.ForumFragment
import com.miu.meditationapp.ui.main.LearnFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var onboardingItemsAdapter: OnboardingItemsAdapter
    private lateinit var indicatorContainer: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


//        supportActionBar?.title = person.firstName
//        supportActionBar?.subtitle = person.profession

        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(HomeFragment(), "Home")
        adapter.addFragment(AboutFragment(), "About")
        adapter.addFragment(LearnFragment(), "Learn")
        adapter.addFragment(ForumFragment(), "Forum")

        viewPager.adapter = adapter

        tabs.setupWithViewPager(viewPager)

        tabs.getTabAt(0)!!.setIcon(R.drawable.home)
        tabs.getTabAt(1)!!.setIcon(R.drawable.about)
        tabs.getTabAt(2)!!.setIcon(R.drawable.work)
        tabs.getTabAt(3)!!.setIcon(R.drawable.contact)
//        if (savedInstanceState == null) {
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.container, MainFragment.newInstance())
//                .commitNow()
//        }

        // startActivity(Intent(applicationContext, LoginActivity::class.java))
//        navController = this.findNavController(R.id.fragment)
//        NavigationUI.setupActionBarWithNavController(this, navController)

        // if(spf !isOnboardSeen) {
        // startActivity(Intent(applicationContext, OnboardingActivity::class.java))}
        //
    }


}