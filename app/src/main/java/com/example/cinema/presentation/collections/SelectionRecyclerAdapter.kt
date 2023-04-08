package com.example.cinema.presentation.collections

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.cinema.R

class SelectionRecyclerAdapter(private val iconsList: List<Int>) :
    RecyclerView.Adapter<SelectionRecyclerAdapter.SelectionViewHolder>() {

    class SelectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.imageIcon)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectionViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.collection_icon_item, parent, false)
        return SelectionViewHolder(view)
    }

    override fun getItemCount(): Int {
        return iconsList.size
    }

    override fun onBindViewHolder(holder: SelectionViewHolder, position: Int) {
        holder.image.setImageResource(iconsList[position])
    }
}
