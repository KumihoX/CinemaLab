package com.example.cinema.presentation.movie.moviedetail

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cinema.R

class FramesRecyclerAdapter(
    private val frames: List<String>,
    private val activity: Activity,
) :
    RecyclerView.Adapter<FramesRecyclerAdapter.FramesViewHolder>() {

    class FramesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageButton = itemView.findViewById(R.id.frameButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FramesViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.frames_horizontal_item, parent, false)
        return FramesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FramesViewHolder, position: Int) {
        Glide.with(activity).load(frames[position]).into(holder.image)

        holder.image.setOnClickListener {
        }
    }

    override fun getItemCount() = frames.size
}
