package com.example.cinema.domain.usecase.refresh

import android.content.Context
import com.example.cinema.data.remote.api.dto.AuthTokenPairDto
import com.example.cinema.domain.repository.AuthRepository
import com.example.cinema.domain.usecase.storage.GetTokenFromLocalStorageUseCase
import javax.inject.Inject

class RefreshTokenUseCase @Inject constructor(private val repository: AuthRepository) {

    suspend operator fun invoke(context: Context): AuthTokenPairDto {
        val getTokenFromLocalStorageUseCase = GetTokenFromLocalStorageUseCase(context)
        val token = getTokenFromLocalStorageUseCase.execute()

        return repository.refresh(token)
    }
}