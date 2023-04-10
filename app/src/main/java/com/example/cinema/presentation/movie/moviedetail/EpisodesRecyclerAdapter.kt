package com.example.cinema.presentation.movie.moviedetail

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cinema.R
import com.example.cinema.data.remote.dto.EpisodeDto
import com.example.cinema.domain.model.Movie

class EpisodesRecyclerAdapter(
    private val episodes: List<EpisodeDto>,
    private val passData: (EpisodeDto) -> Unit
) :
    RecyclerView.Adapter<EpisodesRecyclerAdapter.EpisodesViewHolder>() {

    class EpisodesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val item: View = itemView.findViewById(R.id.episodeItem)
        val image: ImageView = itemView.findViewById(R.id.episodeImage)
        val name: TextView = itemView.findViewById(R.id.episodeName)
        val description: TextView = itemView.findViewById(R.id.episodeDescription)
        val year: TextView = itemView.findViewById(R.id.episodeYear)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodesViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.episode_item, parent, false)
        return EpisodesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: EpisodesViewHolder, position: Int) {
        holder.item.setOnClickListener {
            passData(episodes[position])
        }
        Glide.with(holder.image).load(episodes[position].preview).into(holder.image)
        holder.name.text = episodes[position].name
        holder.description.text = episodes[position].description
        holder.year.text = episodes[position].year
    }

    override fun getItemCount() = episodes.size
}