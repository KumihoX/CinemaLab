package com.example.cinema.domain.repository

import com.example.cinema.data.remote.dto.AuthTokenPairDto
import com.example.cinema.data.remote.dto.RegistrationBodyDto

interface AuthRepository {
    suspend fun register(body: RegistrationBodyDto): AuthTokenPairDto
}