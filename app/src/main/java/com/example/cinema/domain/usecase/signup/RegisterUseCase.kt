package com.example.cinema.domain.usecase.signup

import com.example.cinema.data.remote.api.dto.AuthTokenPairDto
import com.example.cinema.data.remote.api.dto.RegistrationBodyDto
import com.example.cinema.domain.repository.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(userData: RegistrationBodyDto): AuthTokenPairDto =
        repository.register(userData)
}