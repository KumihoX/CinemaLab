package com.example.cinema.data.repository

import com.example.cinema.data.remote.api.EpisodesApi
import com.example.cinema.data.remote.api.dto.AuthTokenPairDto
import com.example.cinema.data.remote.api.dto.EpisodeTimeDto
import com.example.cinema.domain.repository.EpisodesRepository
import javax.inject.Inject

class EpisodesRepositoryImpl @Inject constructor(
    private val api: EpisodesApi
) : EpisodesRepository {
    override suspend fun getEpisodeTime(token: AuthTokenPairDto, episodeId: String): EpisodeTimeDto {
        return api.getEpisodeTime("Bearer ${token.accessToken}", episodeId)
    }

    override suspend fun postEpisodeTime(token: AuthTokenPairDto, episodeId: String, episodeTime: EpisodeTimeDto) {
        return api.postEpisodeTime("Bearer ${token.accessToken}", episodeId, episodeTime)
    }
}