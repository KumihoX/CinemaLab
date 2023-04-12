package com.example.cinema.domain.repository

import com.example.cinema.data.remote.api.dto.AuthCredentialDto
import com.example.cinema.data.remote.api.dto.AuthTokenPairDto
import com.example.cinema.data.remote.api.dto.RegistrationBodyDto

interface AuthRepository {
    suspend fun register(body: RegistrationBodyDto): AuthTokenPairDto

    suspend fun comeIn(body: AuthCredentialDto): AuthTokenPairDto
}