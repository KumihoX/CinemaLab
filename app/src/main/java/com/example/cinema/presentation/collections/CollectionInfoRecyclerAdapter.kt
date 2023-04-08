package com.example.cinema.presentation.collections

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cinema.R
import com.example.cinema.data.remote.dto.CollectionListItemDto
import com.example.cinema.data.remote.dto.MovieDto

class CollectionInfoRecyclerAdapter (
    private val collectionInfo: List<MovieDto>
) :
    RecyclerView.Adapter<CollectionInfoRecyclerAdapter.CollectionInfoViewHolder>() {

    class CollectionInfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cover: ImageView = itemView.findViewById(R.id.collectionInfoCover)
        val name: TextView = itemView.findViewById(R.id.collectionInfoName)
        val description: TextView = itemView.findViewById(R.id.collectionInfoDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionInfoViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.collection_info_item, parent, false)
        return CollectionInfoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CollectionInfoViewHolder, position: Int) {
        Glide.with(holder.cover).load(collectionInfo[position].poster).into(holder.cover)
        holder.name.text = collectionInfo[position].name
        holder.description.text = collectionInfo[position].description

    }

    override fun getItemCount() = collectionInfo.size
}