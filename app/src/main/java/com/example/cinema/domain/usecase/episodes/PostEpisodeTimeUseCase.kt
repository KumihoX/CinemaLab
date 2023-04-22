package com.example.cinema.domain.usecase.episodes

import android.content.Context
import com.example.cinema.data.remote.api.dto.EpisodeTimeDto
import com.example.cinema.domain.repository.EpisodesRepository
import com.example.cinema.domain.usecase.storage.GetTokenFromLocalStorageUseCase
import javax.inject.Inject

class PostEpisodeTimeUseCase @Inject constructor(
    private val repository: EpisodesRepository
) {
    suspend operator fun invoke(
        context: Context,
        episodeId: String,
        episodeTimeDto: EpisodeTimeDto
    ) {
        val getTokenFromLocalStorageUseCase = GetTokenFromLocalStorageUseCase(context)
        val token = getTokenFromLocalStorageUseCase.execute()

        return repository.postEpisodeTime(token, episodeId, episodeTimeDto)
    }

}