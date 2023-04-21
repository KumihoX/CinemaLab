package com.example.cinema.domain.usecase.chats

import android.content.Context
import com.example.cinema.domain.repository.ChatsWebSocketRepository
import com.example.cinema.domain.usecase.storage.GetTokenFromLocalStorageUseCase
import javax.inject.Inject

class OpenWebSocketUseCase @Inject constructor(
    private val repository: ChatsWebSocketRepository
) {
    operator fun invoke(context: Context, chatId: String) {
        val getTokenFromLocalStorageUseCase = GetTokenFromLocalStorageUseCase(context)
        val token = getTokenFromLocalStorageUseCase.execute()

        repository.initializeWebSocket(chatId, token.accessToken)
    }
}