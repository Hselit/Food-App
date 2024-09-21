package com.example.foodapplication.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.foodapplication.fragments.onboard1
import com.example.foodapplication.fragments.onboard2
import com.example.foodapplication.fragments.onboard3

class PageAdapter(fm:FragmentManager):FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getCount():Int{
        return 3
    }

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> onboard1()
            1 -> onboard2()
            2 -> onboard3()
            else -> throw IllegalStateException("Unexpected position $position")
        }
    }
}