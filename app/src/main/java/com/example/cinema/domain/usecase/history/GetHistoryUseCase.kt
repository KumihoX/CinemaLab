package com.example.cinema.domain.usecase.history

import android.content.Context
import com.example.cinema.data.remote.api.dto.EpisodeTimeDto
import com.example.cinema.data.remote.api.dto.EpisodeViewDto
import com.example.cinema.domain.repository.EpisodesRepository
import com.example.cinema.domain.repository.HistoryRepository
import com.example.cinema.domain.usecase.storage.GetTokenFromLocalStorageUseCase
import javax.inject.Inject

class GetHistoryUseCase @Inject constructor(
    private val repository: HistoryRepository
) {
    suspend operator fun invoke(context: Context): List<EpisodeViewDto> {
        val getTokenFromLocalStorageUseCase = GetTokenFromLocalStorageUseCase(context)
        val token = getTokenFromLocalStorageUseCase.execute()

        return repository.getHistory(token)
    }
}