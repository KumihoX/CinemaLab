package com.example.cinema.domain.repository

import com.example.cinema.data.remote.api.dto.AuthTokenPairDto
import com.example.cinema.data.remote.api.dto.UserDto
import okhttp3.MultipartBody
import java.io.File

interface ProfileRepository {
    suspend fun getProfile(token: AuthTokenPairDto): UserDto

    suspend fun postAvatar(token: AuthTokenPairDto, image: MultipartBody.Part)
}