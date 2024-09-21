package com.example.foodapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodapplication.Model.Notification
import com.example.foodapplication.Model.Notification_section
import com.example.foodapplication.R

class NotificationMain(private val items:List<Notification_section>):
    RecyclerView.Adapter<NotificationMain.NotiViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationMain.NotiViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.notification_header,parent,false)

        return NotiViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotificationMain.NotiViewHolder, position: Int) {
        val menuItem = items[position]
        holder.textView.text = menuItem.timereceived


        val list:ArrayList<Notification> = ArrayList()

        if(items.get(position).timereceived.equals("Today")){
            list.add(Notification(R.drawable.green_tick,"Your Order Has Been Taken by the Driver","Recently"))
            list.add(Notification(R.drawable.mail,"Your Order Has Been Taken by the Driver","Recently"))
            list.add(Notification(R.drawable.cross,"Your Order Has Been Canceled","19 Jun 2023"))
        }
        else if (items.get(position).timereceived.equals("Yesterday")){
            list.add(Notification(R.drawable.message,"Your Order Has Been Taken by the Driver","Recently"))
            list.add(Notification(R.drawable.percent,"30% Special Discount!","Special promotion only valid today"))
            list.add(Notification(R.drawable.green_tick,"Your Order Has Been Taken by the Driver","Recently"))
        }

        val adapter = NotificationAdapter(list)
        holder.recyclerView.adapter = adapter
    }

    override fun getItemCount(): Int {
        return items.size
    }


    inner class NotiViewHolder(mitem:View):RecyclerView.ViewHolder(mitem){
        val textView:TextView = itemView.findViewById(R.id.textView17)
        val recyclerView:RecyclerView = itemView.findViewById(R.id.notifyrecycle)

    }
}