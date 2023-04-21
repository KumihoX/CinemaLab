package com.example.cinema.presentation.chats.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.cinema.R
import com.example.cinema.databinding.DateMessageItemBinding
import com.example.cinema.databinding.MyMessageItemBinding
import com.example.cinema.databinding.SomeonesMessageItemBinding
import com.example.cinema.domain.model.ChatItem


class ChatRecyclerAdapter(private val messages: MutableList<ChatItem>) :
    RecyclerView.Adapter<ViewHolder>() {

    class MyMessageViewHolder(itemView: View) : ViewHolder(itemView) {
        private val binding = MyMessageItemBinding.bind(itemView)
        val myName: TextView = binding.myName
        val myMessage: TextView = binding.myMessage
        val myMessageBackground = binding.myMessageBackground
        val imageCardView: View = binding.imageCardView
        val messageCardView: View = binding.messageCardView
        val myAvatar: ImageView = binding.userAvatar
    }

    class SomeonesMessageViewHolder(itemView: View) : ViewHolder(itemView) {
        private val binding = SomeonesMessageItemBinding.bind(itemView)
        val someonesName: TextView = binding.someonesName
        val someonesMessage: TextView = binding.someonesMessage
        val someonesMessageBackground = binding.messageBackground
        val imageCardView: View = binding.imageCardView
        val messageCardView: View = binding.messageCardView
        val someonesAvatar: ImageView = binding.userAvatar
    }

    class DateViewHolder(itemView: View) : ViewHolder(itemView) {
        private val binding = DateMessageItemBinding.bind(itemView)
        val dateText: TextView = binding.dateText
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
                viewHolder.someonesName.text = messages[position].author
                Glide.with(viewHolder.someonesAvatar).load(messages[position].authorAvatar)
                    .into(viewHolder.someonesAvatar)

                val layoutParams =
                    viewHolder.messageCardView.layoutParams as RecyclerView.LayoutParams
                if (messages[position].firstMessage == false) {
                    viewHolder.someonesMessageBackground.setBackgroundResource(R.drawable.someones_message_shape)
                    layoutParams.setMargins(0, 0, 56, 16)
                } else {
                    viewHolder.someonesMessageBackground.setBackgroundResource(R.drawable.someones_first_message_shape)
                    layoutParams.setMargins(0, 16, 56, 16)
                }
                viewHolder.messageCardView.layoutParams = layoutParams

                if (messages[position].avatarIsVisible == true || position == messages.size - 1) {
                    viewHolder.imageCardView.visibility = View.VISIBLE
                } else {
                    viewHolder.imageCardView.visibility = View.INVISIBLE
                }
            }
            1 -> {
                val viewHolder: MyMessageViewHolder = holder as MyMessageViewHolder
                viewHolder.myMessage.text = messages[position].text
                viewHolder.myName.text = messages[position].author
                Glide.with(viewHolder.myAvatar).load(messages[position].authorAvatar)
                    .into(viewHolder.myAvatar)

                val layoutParams =
                    viewHolder.messageCardView.layoutParams as RecyclerView.LayoutParams
                if (messages[position].firstMessage == false) {
                    viewHolder.myMessageBackground.setBackgroundResource(R.drawable.my_message_shape)
                    layoutParams.setMargins(0, 0, 56, 16)
                } else {
                    viewHolder.myMessageBackground.setBackgroundResource(R.drawable.my_first_message_shape)
                    layoutParams.setMargins(0, 16, 56, 16)
                }
                viewHolder.messageCardView.layoutParams = layoutParams

                if (messages[position].avatarIsVisible == true || position == messages.size - 1) {
                    viewHolder.imageCardView.visibility = View.VISIBLE
                } else {
                    viewHolder.imageCardView.visibility = View.INVISIBLE
                }
            }
            2 -> {
                val viewHolder: DateViewHolder = holder as DateViewHolder
                viewHolder.dateText.text = messages[position].text
            }
        }
    }

    override fun getItemCount() = messages.size

    override fun getItemViewType(position: Int): Int {
        return when (messages[position].type) {
            "DATA" -> 2
            "SOMEONE" -> 0
            "MY" -> 1
            else -> 0
        }
    }
}

