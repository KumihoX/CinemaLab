package com.example.cinema.presentation.collections

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cinema.R
import com.example.cinema.data.remote.dto.CollectionListItemDto
import com.example.cinema.data.remote.dto.EpisodeDto

class CollectionsRecyclerAdapter(
    private val collections: List<CollectionListItemDto>
) :
    RecyclerView.Adapter<CollectionsRecyclerAdapter.CollectionsViewHolder>() {

    class CollectionsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.collectionName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionsViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.collection_item, parent, false)
        return CollectionsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CollectionsViewHolder, position: Int) {
        holder.name.text = collections[position].name
    }

    override fun getItemCount() = collections.size
}