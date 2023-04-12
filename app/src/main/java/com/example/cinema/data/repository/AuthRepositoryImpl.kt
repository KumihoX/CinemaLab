package com.example.cinema.data.repository

import com.example.cinema.data.remote.api.AuthApi
import com.example.cinema.data.remote.api.dto.AuthCredentialDto
import com.example.cinema.data.remote.api.dto.AuthTokenPairDto
import com.example.cinema.data.remote.api.dto.RegistrationBodyDto
import com.example.cinema.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApi
) : AuthRepository {
    override suspend fun register(body: RegistrationBodyDto): AuthTokenPairDto {
        return api.register(body)
    }

    override suspend fun comeIn(body: AuthCredentialDto): AuthTokenPairDto {
        return api.comeIn(body)
    }
}