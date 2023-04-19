package com.example.cinema.presentation.chats.chat

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinema.data.remote.api.dto.ChatDto
import com.example.cinema.data.remote.api.dto.MessageDto
import com.example.cinema.domain.usecase.chats.*
import com.example.cinema.presentation.chats.chatlist.ChatListViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import okhttp3.internal.wait
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val openWebSocketUseCase: OpenWebSocketUseCase,
    private val getMessagesUseCase: GetMessagesUseCase,
    private val closeWebSocketUseCase: CloseWebSocketUseCase,
    private val sendMessageUseCase: SendMessageUseCase
) : ViewModel() {

    sealed class ChatState {
        object Loading : ChatState()
        object Success : ChatState()
        class Failure(val errorMessage: String) : ChatState()
    }

    private val _state = MutableLiveData<ChatState>(ChatState.Loading)
    val state: LiveData<ChatState> = _state

    private val _messageList = MutableLiveData<MutableList<MessageDto>>(mutableListOf())
    val messageList: LiveData<MutableList<MessageDto>> = _messageList


    fun openWebSocket(chatId: String) {
        viewModelScope.launch {
            openWebSocketUseCase(context, chatId)
            getMessages()
        }
    }

    fun closeWebSocket() {
        viewModelScope.launch {
            closeWebSocketUseCase()
        }
    }

    private fun getMessages() {
        viewModelScope.launch {
            getMessagesUseCase().collect{
                if (it != null){
                    _messageList.value?.add(it)
                    _state.value = ChatState.Success
                }
            }
        }
    }

    fun sendMessage(message: String){
        viewModelScope.launch {
            sendMessageUseCase(message)
        }
    }
}