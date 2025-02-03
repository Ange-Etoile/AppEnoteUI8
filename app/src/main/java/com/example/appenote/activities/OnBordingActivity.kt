package com.example.appenote.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.appenote.R
import com.example.appenote.adapters.ViewPagerAdapter
import com.example.appenote.models.DataPager
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator

class OnBordingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_on_bording)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val dataPagers: List<DataPager> = listOf(
            DataPager("Welcome To The\nE-note App",  "learn,guarentee \nand dream", R.drawable.education), // Remplacez image1 par vos ressources
            DataPager("Ability to view \ngrades",  "your note accessible \nat any time ", R.drawable.happy_student), // Remplacez image2 par vos ressources
            DataPager("Ability to make \nqueries", "ask,search your \nrequests will always \nbe processed", R.drawable.thesis)  // Remplacez image3 par vos ressources
        )

        val dotsIndicator = findViewById<DotsIndicator>(R.id.dots_indicator)
        val viewPager: ViewPager2 = findViewById(R.id.viewPager)
        val skip: Button = findViewById(R.id.button)

        val adapter = ViewPagerAdapter(dataPagers)
        viewPager.adapter = adapter

        dotsIndicator.attachTo(viewPager)

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val isLastPage = position == adapter.itemCount - 1
                if (isLastPage) {
                    skip.text = "Get started"
                } else {
                    skip.text = "Skip"
                }
            }
        })

        skip.setOnClickListener {
            val intent = Intent(this,HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        changeNavBarColorToPrimary()
    }

    private fun changeNavBarColorToPrimary() {
        val color = ContextCompat.getColor(this, R.color.primary)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.navigationBarColor = (color)
        }
    }
}