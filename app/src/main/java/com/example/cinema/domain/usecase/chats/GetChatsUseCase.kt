package com.example.cinema.domain.usecase.chats

import android.content.Context
import com.example.cinema.data.remote.dto.ChatDto
import com.example.cinema.domain.repository.ChatsRepository
import com.example.cinema.domain.usecase.storage.GetTokenFromLocalStorageUseCase
import javax.inject.Inject

class GetChatsUseCase @Inject constructor(
    private val repository: ChatsRepository
){
    suspend operator fun invoke(
        context: Context
    ): List<ChatDto> {
        val getTokenFromLocalStorageUseCase = GetTokenFromLocalStorageUseCase(context)
        val token = getTokenFromLocalStorageUseCase.execute()

        return repository.getChats(token)
    }

}