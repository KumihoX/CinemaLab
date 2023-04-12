package com.example.cinema.domain.repository

import com.example.cinema.data.remote.api.dto.AuthTokenPairDto
import com.example.cinema.data.remote.api.dto.ChatDto

interface ChatsRepository {
    suspend fun getChats(token: AuthTokenPairDto): List<ChatDto>
}