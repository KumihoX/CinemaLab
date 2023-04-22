package com.example.cinema.presentation.chats.chatlist

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cinema.R
import com.example.cinema.data.remote.api.dto.ChatDto
import com.example.cinema.data.remote.database.entity.CollectionEntity
import com.example.cinema.databinding.ChatListItemBinding

class ChatListRecyclerAdapter(private val chatNames: List<ChatDto>,
                              private val passData: (ChatDto) -> Unit ) :
    RecyclerView.Adapter<ChatListRecyclerAdapter.ChatListViewHolder>() {

    class ChatListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ChatListItemBinding.bind(itemView)
        val item: View = binding.chatListItem
        val chatName: TextView = binding.chatNameText
        val chatLastMessage: TextView = binding.lastMessageText
        val onImageText: TextView = binding.chatAbbreviatedName
    }

    private fun getFirstLetters(shortName: String): String {
        val words = shortName.split(" ")
        var firstLetters = ""
        if (words.size > 2){
            for (i in 0..1) {
                firstLetters += words[i][0].uppercaseChar()
            }
            return firstLetters
        }
        for (i in chatNames.indices) {
            firstLetters += words[i][0].uppercaseChar()
        }
        return firstLetters
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatListViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.chat_list_item, parent, false)
        return ChatListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ChatListViewHolder, position: Int) {
        holder.item.setOnClickListener {
            passData(chatNames[position])
        }
        holder.chatName.text = chatNames[position].chatName
        holder.onImageText.text = getFirstLetters(chatNames[position].chatName)
        val spannable = SpannableString("${chatNames[position].lastMessage!!.authorName}: ${chatNames[position].lastMessage!!.text}")
        spannable.setSpan(
            ForegroundColorSpan(Color.GRAY),
            0, chatNames[position].lastMessage!!.authorName.length + 1,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        holder.chatLastMessage.text = spannable
    }

    override fun getItemCount() = chatNames.size
}