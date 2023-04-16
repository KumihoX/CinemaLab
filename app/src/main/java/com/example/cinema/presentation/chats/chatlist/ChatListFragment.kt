package com.example.cinema.presentation.chats.chatlist

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cinema.R
import com.example.cinema.data.remote.api.dto.ChatDto
import com.example.cinema.databinding.FragmentChatListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatListFragment : Fragment() {
    private lateinit var binding: FragmentChatListBinding
    private val viewModel: ChatListViewModel by viewModels()

    private var callback: ChatsListener? = null

    interface ChatsListener {
        fun backToProfileFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mainView = inflater.inflate(R.layout.fragment_chat_list, container, false)
        binding = FragmentChatListBinding.bind(mainView)

        val stateObserver = Observer<ChatListViewModel.ChatListState> {
            when (it) {
                ChatListViewModel.ChatListState.Loading -> {
                    binding.chatListProgressBar.show()
                    binding.chatListGroup.isGone = true
                }
                is ChatListViewModel.ChatListState.Success -> {
                    addChats(it.chats)
                    setOnBackButtonClickListener()
                    binding.chatListProgressBar.hide()
                    binding.chatListGroup.isGone = false
                }
                is ChatListViewModel.ChatListState.Failure -> {
                    binding.chatListProgressBar.hide()
                    binding.chatListGroup.isGone = false
                    createErrorDialog(it.errorMessage)
                }
            }
        }
        viewModel.state.observe(viewLifecycleOwner, stateObserver)


        return binding.root
    }

    override fun onStart() {
        viewModel.getChats()
        super.onStart()
    }

    override fun onAttach(context: Context) {
        callback = activity as ChatsListener
        super.onAttach(context)
    }

    private fun addChats(chats: List<ChatDto>) {
        val chatListRecyclerView = binding.chatListRecyclerView

        chatListRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        chatListRecyclerView.adapter =
            ChatListRecyclerAdapter(
                chats
            )
    }

    private fun setOnBackButtonClickListener() {
        binding.backChatListButton.setOnClickListener {
            callback?.backToProfileFragment()
        }
    }

    private fun createErrorDialog(message: String) {
        val builder = AlertDialog.Builder(context)

        builder.setTitle(getString(R.string.error))
        builder.setMessage(message)
        builder.show()
    }
}