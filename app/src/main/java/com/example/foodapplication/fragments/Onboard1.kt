package com.example.foodapplication.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.foodapplication.IntroButtons
import com.example.foodapplication.R
import com.example.foodapplication.fragments.onboard2

class onboard1:Fragment() {

    private var listener:IntroButtons?=null

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.onboard1, container, false)

        view.findViewById<Button>(R.id.skip1).setOnClickListener {
            listener?.onskip()
        }

        view.findViewById<Button>(R.id.next1).setOnClickListener {
            listener?.onnext()
        }
        return view
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is IntroButtons) {
            listener = context
        } else {
            throw RuntimeException("$context must implement IntroButtons")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

}