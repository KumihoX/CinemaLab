package com.example.cinema.presentation.chats.chatlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cinema.R
import com.example.cinema.data.remote.api.dto.ChatDto
import com.example.cinema.databinding.ChatListItemBinding

class ChatListRecyclerAdapter(private val chatNames: List<ChatDto>) :
    RecyclerView.Adapter<ChatListRecyclerAdapter.ChatListViewHolder>() {

    class ChatListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ChatListItemBinding.bind(itemView)
        val chatName: TextView = binding.chatNameText
        val chatLastMessage: TextView = binding.lastMessageText
        val onImageText: TextView = binding.chatAbbreviatedName
    }

    private fun getFirstLetters(): String {
        var firstLetters = ""
        for (i in chatNames.indices) {
            firstLetters += chatNames[i].chatName[0]
        }
        return firstLetters
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatListViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.chat_list_item, parent, false)
        return ChatListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ChatListViewHolder, position: Int) {
        holder.chatName.text = chatNames[position].chatName
        holder.onImageText.text = getFirstLetters()
    }

    override fun getItemCount() = chatNames.size
}