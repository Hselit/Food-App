package com.example.foodapplication.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import com.example.foodapplication.R

class Emailverification : Fragment() {

    private lateinit var backButton: ImageView
    private lateinit var verify:Button

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_emailverification, container, false)

        backButton = view.findViewById(R.id.backButton)
        backButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        verify = view.findViewById(R.id.verifyaccbtn)
        verify.setOnClickListener {
            val fragment = ResetPassword()
            parentFragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.verifyacc,fragment)
                .commit()
        }
        return view
    }
}