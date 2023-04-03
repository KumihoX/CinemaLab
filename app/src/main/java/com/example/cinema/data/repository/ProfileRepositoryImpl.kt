package com.example.cinema.data.repository

import com.example.cinema.data.remote.ProfileApi
import com.example.cinema.data.remote.dto.AuthTokenPairDto
import com.example.cinema.data.remote.dto.UserDto
import com.example.cinema.domain.repository.ProfileRepository
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val api: ProfileApi
) : ProfileRepository {
    override suspend fun getProfile(token: AuthTokenPairDto): UserDto {
        return api.getProfile("Bearer ${token.accessToken}")
    }
}