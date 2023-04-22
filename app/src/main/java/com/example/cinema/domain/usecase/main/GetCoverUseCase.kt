package com.example.cinema.domain.usecase.main

import android.content.Context
import com.example.cinema.data.remote.api.dto.CoverMovieDto
import com.example.cinema.domain.repository.CoverRepository
import com.example.cinema.domain.usecase.storage.GetTokenFromLocalStorageUseCase
import javax.inject.Inject

class GetCoverUseCase @Inject constructor(
    private val repository: CoverRepository
) {
    suspend operator fun invoke(context: Context): CoverMovieDto {
        val getTokenFromLocalStorageUseCase = GetTokenFromLocalStorageUseCase(context)
        val token = getTokenFromLocalStorageUseCase.execute()

        return repository.getCover(token)
    }
}
