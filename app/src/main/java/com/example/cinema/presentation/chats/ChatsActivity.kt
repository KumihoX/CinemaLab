package com.example.cinema.presentation.chats

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cinema.databinding.ActivityChatsBinding
import com.example.cinema.presentation.chats.chatlist.ChatListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatsActivity : AppCompatActivity(), ChatListFragment.ChatsListener {
    private lateinit var binding: ActivityChatsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChatsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun backToProfileFragment() {
        finish()
    }
}