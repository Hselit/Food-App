package com.example.foodapplication.fragments

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.example.foodapplication.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ForgotPass2 : BottomSheetDialogFragment() {

    private lateinit var card1: CardView
    private lateinit var cnt: Button
    private lateinit var card2: CardView
    private lateinit var tick1: ImageView
    private lateinit var tick2: ImageView

    private fun movetonext(emailverification: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(android.R.id.content, emailverification)
            .addToBackStack(null)
            .commit()
        dismiss()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_forgot_pass2, container, false)

        cnt = view.findViewById(R.id.continueButton)
        cnt.setOnClickListener {
            movetonext(Emailverification())
        }

        tick1 = view.findViewById(R.id.tickimg1)
        tick1.visibility = View.GONE

        card1 = view.findViewById(R.id.cardview1)
        card1.setBackgroundResource(R.drawable.rect)

        tick2 = view.findViewById(R.id.tickimg2)
        tick2.visibility = View.GONE

        card2 = view.findViewById(R.id.cardview2)
        card2.setBackgroundResource(R.drawable.rect)

        card1.setOnClickListener {
            selectCard(card1, tick1)
            deselectCard(card2, tick2)
        }

        card2.setOnClickListener {
            selectCard(card2, tick2)
            deselectCard(card1, tick1)
        }

        return view
    }

    private fun selectCard(cardView: CardView, tickImageView: ImageView) {
        tickImageView.visibility = View.VISIBLE
        val borderDrawable = GradientDrawable()
        borderDrawable.setStroke(4, Color.parseColor("#FE8C00"))
        borderDrawable.cornerRadius = 20f
        cardView.setBackground(borderDrawable)
    }

    private fun deselectCard(cardView: CardView, tickImageView: ImageView) {
        tickImageView.visibility = View.GONE
        cardView.setBackgroundColor(Color.WHITE)
        cardView.setBackgroundResource(R.drawable.rect)
    }
}
