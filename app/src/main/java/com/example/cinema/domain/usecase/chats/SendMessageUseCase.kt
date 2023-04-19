package com.example.cinema.domain.usecase.chats

import com.example.cinema.domain.repository.ChatsWebSocketRepository
import javax.inject.Inject

class SendMessageUseCase @Inject constructor(
    private val repository: ChatsWebSocketRepository
) {
    operator fun invoke(message: String) {
        repository.sendMessage(message)
    }
}