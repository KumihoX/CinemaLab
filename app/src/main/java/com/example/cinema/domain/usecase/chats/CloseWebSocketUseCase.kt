package com.example.cinema.domain.usecase.chats

import com.example.cinema.domain.repository.ChatsRepository
import com.example.cinema.domain.repository.ChatsWebSocketRepository
import javax.inject.Inject

class CloseWebSocketUseCase @Inject constructor(
    private val repository: ChatsWebSocketRepository
) {
    operator fun invoke() {
        repository.closeSocket()
    }
}