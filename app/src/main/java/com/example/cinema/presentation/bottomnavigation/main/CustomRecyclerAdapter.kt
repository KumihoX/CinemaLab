package com.example.cinema.presentation.bottomnavigation.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cinema.R
import com.example.cinema.domain.model.Movie

class CustomRecyclerAdapter(
    private val covers: List<Movie>,
    private val typeOfRecyclerItems: Int,
    private val passData: (Movie) -> Unit
) :
    RecyclerView.Adapter<CustomRecyclerAdapter.CustomViewHolder>() {

    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageButton = itemView.findViewById(R.id.filmButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(typeOfRecyclerItems, parent, false)
        return CustomViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        Glide.with(holder.image).load(covers[position].poster).into(holder.image)

        holder.image.setOnClickListener {
            passData(covers[position])
        }
    }

    override fun getItemCount() = covers.size
}