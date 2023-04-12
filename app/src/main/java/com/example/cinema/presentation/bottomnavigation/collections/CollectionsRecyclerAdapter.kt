package com.example.cinema.presentation.bottomnavigation.collections

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cinema.R
import com.example.cinema.data.remote.api.dto.CollectionListItemDto
import com.example.cinema.data.remote.database.entity.CollectionEntity

class CollectionsRecyclerAdapter(
    private val collections: List<CollectionEntity>,
    private val passData: (CollectionEntity) -> Unit
) :
    RecyclerView.Adapter<CollectionsRecyclerAdapter.CollectionsViewHolder>() {

    class CollectionsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val item: View = itemView.findViewById(R.id.collectionItem)
        val name: TextView = itemView.findViewById(R.id.collectionName)
        val icon: ImageView = itemView.findViewById(R.id.collectionIcon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionsViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.collection_item, parent, false)
        return CollectionsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CollectionsViewHolder, position: Int) {
        holder.item.setOnClickListener {
            passData(collections[position])
        }
        holder.icon.setImageResource(collections[position].imageId)
        holder.name.text = collections[position].name
    }

    override fun getItemCount() = collections.size
}