package com.example.foodapplication.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.example.foodapplication.R

class ResetPassword : Fragment() {

    private lateinit var backButton: ImageView
    private lateinit var cont:Button
    private lateinit var etpass:EditText
    private lateinit var etpass2:EditText

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_reset_password, container, false)

        backButton = view.findViewById(R.id.backButton)
        backButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        cont = view.findViewById(R.id.registerbtn)
        cont.setOnClickListener {
            openbtmdialog()
        }

        etpass = view.findViewById(R.id.newpass)
        var isPasswordVisible = false
        etpass.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= (etpass.right - etpass.compoundDrawables[2].bounds.width())) {
                    isPasswordVisible = !isPasswordVisible
                    if (isPasswordVisible) {
                        etpass.transformationMethod = HideReturnsTransformationMethod.getInstance()
                        etpass.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.key_open, 0)
                    } else {
                        etpass.transformationMethod = PasswordTransformationMethod.getInstance()
                        etpass.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.key_close, 0)
                    }
                    etpass.setSelection(etpass.text.length)
                    return@setOnTouchListener true
                }
            }
            return@setOnTouchListener false
        }

        var isPasswordVisible2 = false
        etpass2 = view.findViewById(R.id.confirmpass)
        etpass2.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= (etpass2.right - etpass2.compoundDrawables[2].bounds.width())) {
                    isPasswordVisible = !isPasswordVisible
                    if (isPasswordVisible) {
                        etpass2.transformationMethod = HideReturnsTransformationMethod.getInstance()
                        etpass2.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.key_open, 0)
                    } else {
                        etpass2.transformationMethod = PasswordTransformationMethod.getInstance()
                        etpass2.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.key_close, 0)
                    }
                    etpass2.setSelection(etpass2.text.length)
                    return@setOnTouchListener true
                }
            }
            return@setOnTouchListener false
        }
        return view
    }

    private fun openbtmdialog() {
        val bottomdialog = PasswordChanged()
        bottomdialog.show(parentFragmentManager,bottomdialog.tag)
    }
}