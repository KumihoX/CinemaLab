package com.example.cinema.presentation.chats.chat

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cinema.R
import com.example.cinema.data.remote.api.dto.MessageDto
import com.example.cinema.databinding.FragmentChatBinding
import com.example.cinema.domain.model.ChatItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatFragment : Fragment() {
    private lateinit var binding: FragmentChatBinding
    private val viewModel: ChatViewModel by viewModels()

    private val args: ChatFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mainView = inflater.inflate(R.layout.fragment_chat, container, false)
        binding = FragmentChatBinding.bind(mainView)

        val stateObserver = Observer<ChatViewModel.ChatState> {
            when (it) {
                ChatViewModel.ChatState.Loading -> {
                    binding.chatProgressBar.show()
                    binding.chatGroup.isGone = true
                }
                ChatViewModel.ChatState.MessageLoading -> {
                    binding.chatProgressBar.show()
                }
                ChatViewModel.ChatState.Success -> {
                    addMessages()
                    setOnButtonsClickListener()
                    binding.chatProgressBar.hide()
                    binding.chatGroup.isGone = false
                }
                is ChatViewModel.ChatState.Failure -> {
                    binding.chatProgressBar.hide()
                    binding.chatGroup.isGone = false
                    createErrorDialog(it.errorMessage)
                }
            }
        }
        viewModel.state.observe(viewLifecycleOwner, stateObserver)

        return binding.root
    }

    override fun onStart() {
        viewModel.openWebSocket(args.chatInfo.chatId)
        super.onStart()
    }

    override fun onPause() {
        viewModel.closeWebSocket()
        super.onPause()
    }

    private fun addMessages() {
        val messagesObserver = Observer<MutableList<ChatItem>> {
            val chatRecyclerView = binding.chatRecyclerView

            chatRecyclerView.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            chatRecyclerView.adapter =
                ChatRecyclerAdapter(it)
        }
        viewModel.messageList.observe(viewLifecycleOwner, messagesObserver)
    }

    private fun setOnButtonsClickListener() {
        setOnBackButtonClickListener()
        setOnSendButtonClickListener()
    }

    private fun setOnBackButtonClickListener() {
        binding.backChatButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setOnSendButtonClickListener() {
        binding.sendButton.setOnClickListener {
            viewModel.sendMessage(binding.messageEditText.text.toString())
            binding.messageEditText.setText("")
        }
    }

    private fun createErrorDialog(message: String) {
        val builder = AlertDialog.Builder(context)

        builder.setTitle(getString(R.string.error))
        builder.setMessage(message)
        builder.show()
    }
}