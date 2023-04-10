package com.example.cinema.data.repository

import com.example.cinema.data.remote.ChatsApi
import com.example.cinema.data.remote.dto.AuthTokenPairDto
import com.example.cinema.data.remote.dto.ChatDto
import com.example.cinema.domain.repository.ChatsRepository
import javax.inject.Inject

class ChatsRepositoryImpl @Inject constructor(
    private val api: ChatsApi
): ChatsRepository {
    override suspend fun getChats(token: AuthTokenPairDto): List<ChatDto> {
        return api.getChats("Bearer ${token.accessToken}")
    }
}