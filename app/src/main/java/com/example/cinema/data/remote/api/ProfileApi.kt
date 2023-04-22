package com.example.cinema.data.remote.api

import com.example.cinema.data.remote.api.dto.UserDto
import okhttp3.MultipartBody
import retrofit2.http.*

interface ProfileApi {
    @GET("profile")
    suspend fun getProfile(@Header("Authorization") token: String): UserDto

    @Multipart
    @POST("profile/avatar")
    suspend fun postAvatar(
        @Header("Authorization") token: String,
        @Part image: MultipartBody.Part
    )
}