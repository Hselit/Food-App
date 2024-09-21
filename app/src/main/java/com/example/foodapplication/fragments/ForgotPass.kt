package com.example.foodapplication.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.foodapplication.R

class ForgotPass : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_forgot_pass, container, false)

        view.findViewById<Button>(R.id.registerbtn)
            .setOnClickListener {
                showBottomDialog()
            }
        return view;
    }

    private fun showBottomDialog(){
        val btm = ForgotPass2()
        btm.show(parentFragmentManager,btm.tag)
    }
}