package com.example.cinema.data.remote.api

import com.example.cinema.data.remote.api.dto.ChatDto
import retrofit2.http.GET
import retrofit2.http.Header

interface ChatsApi {
    @GET("chats")
    suspend fun getChats(@Header("Authorization") token: String): List<ChatDto>
}