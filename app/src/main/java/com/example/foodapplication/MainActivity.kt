package com.example.foodapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager.widget.ViewPager
import com.example.foodapplication.activity.LoginActivity
import com.example.foodapplication.adapter.PageAdapter

class MainActivity : AppCompatActivity(),IntroButtons {

    private lateinit var viewPager: ViewPager
    private lateinit var pageAdapter: PageAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewPager = findViewById(R.id.viewpager)
        pageAdapter = PageAdapter(supportFragmentManager)
        viewPager.adapter = pageAdapter
    }

    override fun onskip() {
        startActivity(Intent(this, LoginActivity::class.java))

    }

    override fun onnext() {
        if (::viewPager.isInitialized) {
            val currentItem = viewPager.currentItem
            if (currentItem < pageAdapter.count - 1) {
                viewPager.currentItem = currentItem + 1
            }
        } else {
            // Handle the case where viewPager is not initialized
            Log.e("MainActivity", "viewPager is not initialized")
        }
    }

}