package com.example.cinema.data.repository

import android.util.Log
import com.example.cinema.data.remote.api.dto.MessageDto
import com.example.cinema.data.websocket.ChatsWebSocket
import com.example.cinema.domain.repository.ChatsWebSocketRepository
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ChatsWebSocketRepositoryImpl @Inject constructor(
    private val chatsWebSocket: ChatsWebSocket
) : ChatsWebSocketRepository {
    override fun getMessages(): Flow<MessageDto?> {
        return flow {
            chatsWebSocket.getMessages().collect{
                Log.d("SOCKET", it)
                if (it != "Switching Protocols"){
                    emit(Gson().fromJson(it, MessageDto::class.java))
                }
            }
        }
    }

    override fun initializeWebSocket(chatId: String, token: String) {
        chatsWebSocket.initializeWebSocket(chatId, token)
    }

    override fun sendMessage(message: String) {
        chatsWebSocket.sendMessage(message)
    }

    override fun closeSocket() {
        chatsWebSocket.close()
    }
}