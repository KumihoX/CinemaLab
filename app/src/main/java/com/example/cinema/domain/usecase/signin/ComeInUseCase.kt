package com.example.cinema.domain.usecase.signin

import com.example.cinema.data.remote.dto.AuthCredentialDto
import com.example.cinema.data.remote.dto.AuthTokenPairDto
import com.example.cinema.domain.repository.AuthRepository
import javax.inject.Inject

class ComeInUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(userData: AuthCredentialDto): AuthTokenPairDto =
        repository.comeIn(userData)
}
