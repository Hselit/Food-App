package com.example.foodapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodapplication.Model.Notification
import com.example.foodapplication.R

class NotificationAdapter(private val items: ArrayList<Notification>):
    RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.notification_layout,parent,false)
        return NotificationViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        val menuItem = items[position]
        holder.imageView.setImageResource(menuItem.imgurl)
        holder.txtView.text = menuItem.msghead
        holder.txtView2.text = menuItem.msgsub
    }

    inner class NotificationViewHolder(itmview:View):RecyclerView.ViewHolder(itmview) {

      val imageView:ImageView = itemView.findViewById(R.id.notify_symbol)
      val txtView:TextView = itemView.findViewById(R.id.mainhead)
      val txtView2:TextView = itemView.findViewById(R.id.desc)

    }
}