package com.example.cinema.data.websocket

import com.example.cinema.common.Constants.SOCKET_URL
import kotlinx.coroutines.flow.SharedFlow
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket

class ChatsWebSocket(
    private val okHttpClient: OkHttpClient,
    private val chatsWebSocketListener: ChatsWebSocketListener
) {
    private lateinit var socket: WebSocket

    fun initializeWebSocket(chatId: String, token: String) {
        val url = "$SOCKET_URL$chatId/messages"
        val request = Request.Builder().url(url).addHeader("Authorization", "Bearer $token").build()
        socket = okHttpClient.newWebSocket(request, chatsWebSocketListener)

    }

    fun getMessages(): SharedFlow<String> = chatsWebSocketListener.getMessages()

    fun sendMessage(message: String) = socket.send(message)

    fun close() = socket.close(1000, "Successful")
}