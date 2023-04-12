package com.example.cinema.data.remote.api

import com.example.cinema.data.remote.api.dto.UserDto
import retrofit2.http.GET
import retrofit2.http.Header

interface ProfileApi {
    @GET("profile")
    suspend fun getProfile(@Header("Authorization") token: String): UserDto
}