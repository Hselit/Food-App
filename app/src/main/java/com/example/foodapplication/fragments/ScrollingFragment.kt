package com.example.foodapplication.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.foodapplication.R
import com.example.foodapplication.activity.HomeActivity

class ScrollingFragment : Fragment() {

    private lateinit var tvname:TextView
    private lateinit var tvprice:TextView
    private lateinit var tvmetre:TextView
    private lateinit var tvrating:TextView
    private lateinit var ivmain:ImageView
    private lateinit var ivlike:ImageView
    private lateinit var back:ImageView

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as? HomeActivity)?.hidebottomnavbar()
        val view = inflater.inflate(R.layout.fragment_scrolling, container, false)

        val iname = arguments?.getString("itemName")
        val iprice = arguments?.getString("itemPrice")
        val imeter = arguments?.getString("itemMetre")
        val irating = arguments?.getString("itemRating")
        val iImg = arguments?.getInt("itemImage")
        val ilike = arguments?.getInt("itemLike")

        back = view.findViewById(R.id.backBtn)
        back.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        tvname = view.findViewById(R.id.tvMenuName)
        tvmetre = view.findViewById(R.id.tvMetre)
        tvprice = view.findViewById(R.id.tvPrice)
        tvrating = view.findViewById(R.id.tvRating)
        ivmain = view.findViewById(R.id.imgmain)
        ivlike = view.findViewById(R.id.ivheart)

        tvname.text = iname
        tvmetre.text = imeter
        tvprice.text = iprice
        tvrating.text = irating
        ivmain.setImageResource(iImg?:R.drawable.basess)
        ivlike.setImageResource(ilike?:R.drawable.biglike)

        return view
    }


}