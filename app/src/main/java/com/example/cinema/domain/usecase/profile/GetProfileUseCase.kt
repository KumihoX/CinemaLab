package com.example.cinema.domain.usecase.profile

import android.content.Context
import com.example.cinema.data.remote.dto.UserDto
import com.example.cinema.domain.repository.ProfileRepository
import com.example.cinema.domain.usecase.token.GetTokenFromLocalStorageUseCase
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(context: Context): UserDto {
        val getTokenFromLocalStorageUseCase = GetTokenFromLocalStorageUseCase(context)
        val token = getTokenFromLocalStorageUseCase.execute()

        return repository.getProfile(token)
    }
}