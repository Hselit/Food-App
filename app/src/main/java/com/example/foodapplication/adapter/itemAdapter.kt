package com.example.foodapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodapplication.Model.Items
import com.example.foodapplication.R

class ItemAdapters(private val items: List<Items>,private val itemclicklistener:(Items)->Unit) : RecyclerView.Adapter<com.example.foodapplication.adapter.ItemAdapters.ItemHolder>() {

    var selected:Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val vie = LayoutInflater.from(parent.context)
            .inflate(R.layout.items,parent,false)
        return ItemHolder(vie)
    }

    override fun getItemCount(): Int {
            return items.size
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val menui = items[position]
        holder.image.setImageResource(menui.mimg)
        holder.name.text = menui.name
        holder.rate.text = menui.rating
        holder.pri.text = menui.price
        holder.m.text = menui.meter
        holder.hear.setImageResource(menui.like)
        holder.hear.setOnClickListener {
            if(selected==false) {
                holder.hear.setImageResource(R.drawable.filllike)
                selected =true;
            }else {
                holder.hear.setImageResource(R.drawable.like)
                selected=false
            }
        }
        holder.itemView.setOnClickListener {
            itemclicklistener(menui)
        }
    }

    inner class ItemHolder(itemview:View):RecyclerView.ViewHolder(itemview){
            val image:ImageView = itemView.findViewById(R.id.imageView6)
            val hear:ImageView = itemView.findViewById(R.id.heart)
            val name:TextView = itemView.findViewById(R.id.textView12)
            val rate:TextView = itemView.findViewById(R.id.textView15)
            val m:TextView = itemView.findViewById(R.id.loca)
            val pri:TextView = itemView.findViewById(R.id.price)

        }
}