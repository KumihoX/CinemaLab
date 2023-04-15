package com.example.cinema.domain.repository

import com.example.cinema.data.remote.api.dto.AuthTokenPairDto
import com.example.cinema.data.remote.api.dto.EpisodeTimeDto

interface EpisodesRepository {
    suspend fun getEpisodeTime(token: AuthTokenPairDto, episodeId: String): EpisodeTimeDto

    suspend fun postEpisodeTime(token: AuthTokenPairDto, episodeId: String, episodeTime: EpisodeTimeDto)
}