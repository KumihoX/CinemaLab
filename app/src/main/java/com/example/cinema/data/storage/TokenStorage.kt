package com.example.cinema.data.storage

import com.example.cinema.data.remote.api.dto.AuthTokenPairDto

interface TokenStorage {
    companion object {
        const val TOKEN_KEY = "userToken"
        const val REFRESH_TOKEN_KEY = "refreshUserToken"
        const val TOKEN_KEY_TIME = "userTokenTime"
        const val REFRESH_TOKEN_KEY_TIME = "refreshUserTokenTime"
    }

    fun saveToken(token: AuthTokenPairDto)

    fun getToken(): AuthTokenPairDto

    fun deleteToken()
}