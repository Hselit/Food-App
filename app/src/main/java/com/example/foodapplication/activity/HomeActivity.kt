package com.example.foodapplication.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.foodapplication.R
import com.example.foodapplication.fragments.CartFragment
import com.example.foodapplication.fragments.MessageFragment
import com.example.foodapplication.fragments.menuitemfragment
import com.example.foodapplication.fragments.SearchFragment
import com.example.foodapplication.fragments.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        bottomNavigationView = findViewById(R.id.bottm_menu)

        // Set the default fragment
        if (savedInstanceState == null) {
            loadFragment(menuitemfragment())
        }

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    loadFragment(menuitemfragment())
                    true
                }
                R.id.cart -> {
                    loadFragment(CartFragment())
                    true
                }
                R.id.profile -> {
                    loadFragment(ProfileFragment())
                    true
                }
                R.id.message ->{
                    loadFragment(MessageFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_containers, fragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .addToBackStack(null)
            .commit()
    }

    fun hidebottomnavbar(){
        bottomNavigationView.visibility = View.GONE
    }


    fun showbottomnavbar(){
        bottomNavigationView.visibility = View.VISIBLE
    }
}
