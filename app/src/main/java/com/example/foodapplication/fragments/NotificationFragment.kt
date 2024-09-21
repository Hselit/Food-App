package com.example.foodapplication.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodapplication.Model.Notification
import com.example.foodapplication.Model.Notification_section
import com.example.foodapplication.R
import com.example.foodapplication.adapter.NotificationAdapter
import com.example.foodapplication.adapter.NotificationMain

class NotificationFragment : Fragment() {

    private var list: ArrayList<Notification_section> = ArrayList()
    private lateinit var recycler:RecyclerView
    private lateinit var imageview:ImageView
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_notification, container, false)
        recycler = view.findViewById(R.id.re)

        imageview =view.findViewById(R.id.backBtn)
        imageview.setOnClickListener {
                parentFragmentManager.popBackStack()
        }
        
        list.add(Notification_section("Today"))
        list.add(Notification_section("Yesterday"))
        recycler.adapter = NotificationMain(list)
            
        return view
    }


}