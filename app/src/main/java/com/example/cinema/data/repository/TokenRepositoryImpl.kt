package com.example.cinema.data.repository

import android.content.Context
import com.example.cinema.data.remote.dto.AuthTokenPairDto
import com.example.cinema.data.storage.SharedPreferencesStorage

class TokenRepositoryImpl(
    context: Context
) {
    private val sharedPreferencesStorage by lazy {
        SharedPreferencesStorage(context)
    }

    fun saveTokenToLocalStorage(token: AuthTokenPairDto) {
        sharedPreferencesStorage.saveToken(token)
    }

    fun getTokenFromLocalStorage(): AuthTokenPairDto {
        return sharedPreferencesStorage.getToken()
    }

    fun deleteTokenFromLocalStorage() {
        sharedPreferencesStorage.deleteToken()
    }
}