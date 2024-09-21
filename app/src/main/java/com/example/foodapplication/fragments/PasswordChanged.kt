package com.example.foodapplication.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.foodapplication.R
import com.example.foodapplication.activity.LoginActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class PasswordChanged : BottomSheetDialogFragment(){

    private lateinit var tologin:Button

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_password_changed, container, false)

        tologin = view.findViewById(R.id.backtologin)
        tologin.setOnClickListener {
            gottologin()
        }

        return view
    }

    private fun gottologin() {
        val intent = Intent(requireContext(),LoginActivity::class.java)
        startActivity(intent)
    }
}