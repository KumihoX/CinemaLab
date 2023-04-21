package com.example.cinema.domain.usecase.storage

import android.content.Context
import com.example.cinema.data.remote.api.dto.AuthTokenPairDto
import com.example.cinema.data.repository.TokenRepositoryImpl

class GetTokenFromLocalStorageUseCase(
    private val context: Context
) {

    private val tokenRepositoryImpl by lazy {
        TokenRepositoryImpl(context)
    }

    fun execute(): AuthTokenPairDto {
        return tokenRepositoryImpl.getTokenFromLocalStorage()
    }
}