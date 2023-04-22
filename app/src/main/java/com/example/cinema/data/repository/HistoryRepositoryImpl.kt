package com.example.cinema.data.repository

import com.example.cinema.data.remote.api.HistoryApi
import com.example.cinema.data.remote.api.dto.AuthTokenPairDto
import com.example.cinema.data.remote.api.dto.EpisodeViewDto
import com.example.cinema.domain.repository.HistoryRepository
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(
    private val api: HistoryApi
) : HistoryRepository {
    override suspend fun getHistory(token: AuthTokenPairDto): List<EpisodeViewDto> {
        return api.getHistory("Bearer ${token.accessToken}")
    }
}