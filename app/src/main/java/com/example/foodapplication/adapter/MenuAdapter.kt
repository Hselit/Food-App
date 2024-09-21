package com.example.foodapplication.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodapplication.Model.MenuItems
import com.example.foodapplication.R
import com.google.android.material.card.MaterialCardView

class MenuAdapter(private val menuItems: List<MenuItems>): RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    private var selectedPosition = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.menu_items, parent, false)
        return MenuViewHolder(view)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val menuItem = menuItems[position]
        holder.bind(menuItem)

        // Update the background color of the card view
        holder.itemView.findViewById<CardView>(R.id.card_view).setCardBackgroundColor(
            if (position == selectedPosition) Color.parseColor("#FE8C00") else Color.WHITE
        )

        holder.itemView.setOnClickListener {
            val previousPosition = selectedPosition
            selectedPosition = if (selectedPosition == position) {
                RecyclerView.NO_POSITION
            } else {
                position
            }
            notifyItemChanged(previousPosition)
            notifyItemChanged(selectedPosition)
        }
    }

    override fun getItemCount(): Int {
        return menuItems.size
    }

    inner class MenuViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(menuItem: MenuItems) {
            itemView.findViewById<TextView>(R.id.textViewMenuName).text = menuItem.name
            itemView.findViewById<ImageView>(R.id.imgviewmi).setImageResource(menuItem.imgresid)
        }
    }
}
