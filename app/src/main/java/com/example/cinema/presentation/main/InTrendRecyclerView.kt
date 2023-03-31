package com.example.cinema.presentation.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cinema.R

class InTrendRecyclerView(private val covers: List<String>, private val fragment: Fragment, private val typeOfRecyclerItems: Int) :
    RecyclerView.Adapter<InTrendRecyclerView.InTrendViewHolder>() {

    class InTrendViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageButton = itemView.findViewById(R.id.filmButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InTrendViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(typeOfRecyclerItems, parent, false)
        return InTrendViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: InTrendViewHolder, position: Int) {
        Glide.with(fragment).load(covers[position]).into(holder.image)
    }

    override fun getItemCount() = covers.size
}