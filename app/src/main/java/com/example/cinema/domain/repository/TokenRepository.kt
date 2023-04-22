package com.example.cinema.domain.repository

import com.example.cinema.data.remote.api.dto.AuthTokenPairDto

interface TokenRepository {
    fun saveTokenToLocalStorage(token: AuthTokenPairDto)

    fun getTokenFromLocalStorage(): AuthTokenPairDto

    fun deleteTokenFromLocalStorage()
}