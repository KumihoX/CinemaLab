package com.example.cinema.domain.usecase.token

import android.content.Context
import com.example.cinema.data.remote.dto.AuthTokenPairDto
import com.example.cinema.data.repository.TokenRepositoryImpl

class SaveTokenUseCase (private val context: Context) {

    private val tokenRepository by lazy {
        TokenRepositoryImpl(context)
    }

    fun execute(token: AuthTokenPairDto) {
        tokenRepository.saveTokenToLocalStorage(token)
    }
}