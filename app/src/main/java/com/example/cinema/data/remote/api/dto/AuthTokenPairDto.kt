package com.example.cinema.data.remote.api.dto

data class AuthTokenPairDto(
    val accessToken: String,
    val accessTokenExpiresIn: Int,
    val refreshToken: String,
    val refreshTokenExpiresIn: Int
)
