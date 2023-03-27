package com.example.cinema.domain.usecase.signup

import com.example.cinema.data.remote.dto.AuthTokenPairDto
import com.example.cinema.data.remote.dto.RegistrationBodyDto
import com.example.cinema.domain.repository.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(userData: RegistrationBodyDto): AuthTokenPairDto =
        repository.register(userData)
}