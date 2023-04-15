package com.example.cinema.domain.usecase.episodes

import android.content.Context
import com.example.cinema.data.remote.api.dto.EpisodeTimeDto
import com.example.cinema.data.remote.api.dto.MovieValueDto
import com.example.cinema.domain.repository.CollectionRepository
import com.example.cinema.domain.repository.EpisodesRepository
import com.example.cinema.domain.usecase.storage.GetTokenFromLocalStorageUseCase
import javax.inject.Inject

class GetEpisodeTimeUseCase @Inject constructor(
    private val repository: EpisodesRepository
) {
    suspend operator fun invoke(
        context: Context,
        episodeId: String
    ): EpisodeTimeDto {
        val getTokenFromLocalStorageUseCase = GetTokenFromLocalStorageUseCase(context)
        val token = getTokenFromLocalStorageUseCase.execute()

        return repository.getEpisodeTime(token, episodeId)
    }
}