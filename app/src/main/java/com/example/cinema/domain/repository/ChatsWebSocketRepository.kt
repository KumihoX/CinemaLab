package com.example.cinema.domain.repository

import com.example.cinema.data.remote.api.dto.MessageDto
import kotlinx.coroutines.flow.Flow

interface ChatsWebSocketRepository {
    fun getMessages(): Flow<MessageDto?>

    fun initializeWebSocket(chatId: String, token: String)

    fun sendMessage(message: String)

    fun closeSocket()
}