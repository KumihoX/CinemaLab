package com.example.cinema.presentation.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.core.content.ContextCompat.startActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cinema.R
import com.example.cinema.data.remote.dto.MovieDto
import com.example.cinema.presentation.moviedetail.MovieDetailActivity

class CustomRecyclerAdapter(
    private val covers: List<MovieDto>,
    private val fragment: Fragment,
    private val typeOfRecyclerItems: Int,
    private val navController: NavController,
    private val passData: (MovieDto) -> Unit
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
        Glide.with(fragment).load(covers[position].poster).into(holder.image)

        holder.image.setOnClickListener {
            passData(covers[position])
        }
    }

    override fun getItemCount() = covers.size
}