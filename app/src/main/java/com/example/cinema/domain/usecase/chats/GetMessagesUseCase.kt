package com.example.cinema.domain.usecase.chats

import com.example.cinema.data.remote.api.dto.MessageDto
import com.example.cinema.domain.repository.ChatsWebSocketRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMessagesUseCase @Inject constructor(
    private val repository: ChatsWebSocketRepository
) {
    operator fun invoke(): Flow<MessageDto?> {
        return repository.getMessages()
    }
}