package com.example.cinema.domain.repository

import com.example.cinema.data.remote.api.dto.AuthTokenPairDto
import com.example.cinema.data.remote.api.dto.EpisodeViewDto

interface HistoryRepository {
    suspend fun getHistory(token: AuthTokenPairDto): List<EpisodeViewDto>
}