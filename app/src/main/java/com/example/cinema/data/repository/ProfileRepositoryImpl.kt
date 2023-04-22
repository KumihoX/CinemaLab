package com.example.cinema.data.repository

import com.example.cinema.data.remote.api.ProfileApi
import com.example.cinema.data.remote.api.dto.AuthTokenPairDto
import com.example.cinema.data.remote.api.dto.UserDto
import com.example.cinema.domain.repository.ProfileRepository
import okhttp3.MultipartBody
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val api: ProfileApi
) : ProfileRepository {
    override suspend fun getProfile(token: AuthTokenPairDto): UserDto {
        return api.getProfile("Bearer ${token.accessToken}")
    }

    override suspend fun postAvatar(token: AuthTokenPairDto, image: MultipartBody.Part) {
        api.postAvatar("Bearer ${token.accessToken}", image)
    }
}