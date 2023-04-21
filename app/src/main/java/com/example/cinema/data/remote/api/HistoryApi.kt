package com.example.cinema.data.remote.api

import com.example.cinema.data.remote.api.dto.EpisodeViewDto
import retrofit2.http.GET
import retrofit2.http.Header

interface HistoryApi {
    @GET("history")
    suspend fun getHistory(@Header("Authorization") token: String): List<EpisodeViewDto>
}