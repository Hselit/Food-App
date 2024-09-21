package com.example.foodapplication.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.example.foodapplication.R
import com.example.foodapplication.activity.LoginActivity

class onboard3 : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.onboard3,container,false)

        view.findViewById<ImageButton>(R.id.imgbtnst).setOnClickListener{
            startActivity(Intent(activity, LoginActivity::class.java))
            requireActivity().finish()
        }

        return view
    }
}