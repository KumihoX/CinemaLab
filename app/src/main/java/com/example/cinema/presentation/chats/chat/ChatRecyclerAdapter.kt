package com.example.cinema.presentation.chats.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.cinema.R
import com.example.cinema.data.remote.api.dto.ChatDto
import com.example.cinema.data.remote.api.dto.MessageDto
import com.example.cinema.databinding.ChatListItemBinding
import com.example.cinema.databinding.SomeonesMessageItemBinding

class ChatRecyclerAdapter(private val messages: MutableList<MessageDto>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class MyMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ChatListItemBinding.bind(itemView)
        val chatName: TextView = binding.chatNameText
        val chatLastMessage: TextView = binding.lastMessageText
        val onImageText: TextView = binding.chatAbbreviatedName
    }

    class SomeonesMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = SomeonesMessageItemBinding.bind(itemView)
        val someonesName: TextView = binding.someonesName
        val someonesMessage: TextView = binding.someonesMessage
        val cardView: View = binding.cardView
    }

    class DateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ChatListItemBinding.bind(itemView)
        val chatName: TextView = binding.chatNameText
        val chatLastMessage: TextView = binding.lastMessageText
        val onImageText: TextView = binding.chatAbbreviatedName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        when (viewType) {
            0 -> {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.someones_message_item, parent, false)
                return SomeonesMessageViewHolder(itemView)
            }
            1 -> {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.my_message_item, parent, false)
                return MyMessageViewHolder(itemView)
            }
            2 -> {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.date_message_item, parent, false)
                return DateViewHolder(itemView)
            }
            else -> {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.someones_message_item, parent, false)
                return SomeonesMessageViewHolder(itemView)
            }
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder.itemViewType) {
            0 -> {
                val viewHolder: SomeonesMessageViewHolder = holder as SomeonesMessageViewHolder
                viewHolder.someonesMessage.text = messages[position].text
            }
            1 -> {

            }
            2 -> {}
            else -> {}
        }
    }

    override fun getItemCount() = messages.size

    override fun getItemViewType(position: Int): Int {
        return 0
    }
}

