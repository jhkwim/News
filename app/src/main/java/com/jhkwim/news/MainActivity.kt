package com.jhkwim.news

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.jhkwim.news.api.NaverAPI

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = ViewPagerAdapter(this)

        val pager = findViewById<ViewPager2>(R.id.pager_fragment)
        pager.adapter = adapter

    }
}