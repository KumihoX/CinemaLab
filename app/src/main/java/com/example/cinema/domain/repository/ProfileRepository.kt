package com.example.cinema.domain.repository

import com.example.cinema.data.remote.api.dto.AuthTokenPairDto
import com.example.cinema.data.remote.api.dto.UserDto

interface ProfileRepository {
    suspend fun getProfile(token: AuthTokenPairDto): UserDto
}