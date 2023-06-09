package com.example.cinema.domain.usecase.storage

import android.content.Context
import com.example.cinema.data.remote.api.dto.AuthTokenPairDto
import com.example.cinema.data.repository.TokenRepositoryImpl

class SaveTokenUseCase(private val context: Context) {

    private val tokenRepository by lazy {
        TokenRepositoryImpl(context)
    }

    fun execute(token: AuthTokenPairDto) {
        tokenRepository.saveTokenToLocalStorage(token)
    }
}