package com.example.cinema.data.repository

import android.content.Context
import com.example.cinema.data.remote.dto.AuthTokenPairDto
import com.example.cinema.data.storage.SharedPreferencesStorage
import com.example.cinema.domain.repository.TokenRepository

class TokenRepositoryImpl(
    context: Context
) : TokenRepository {
    private val sharedPreferencesStorage by lazy {
        SharedPreferencesStorage(context)
    }

    override fun saveTokenToLocalStorage(token: AuthTokenPairDto) {
        sharedPreferencesStorage.saveToken(token)
    }

    override fun getTokenFromLocalStorage(): AuthTokenPairDto {
        return sharedPreferencesStorage.getToken()
    }

    override fun deleteTokenFromLocalStorage() {
        sharedPreferencesStorage.deleteToken()
    }
}