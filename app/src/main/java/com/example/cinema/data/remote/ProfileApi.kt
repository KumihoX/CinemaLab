package com.example.cinema.data.remote

import com.example.cinema.data.remote.dto.UserDto
import retrofit2.http.GET
import retrofit2.http.Header

interface ProfileApi {
    @GET("profile")
    suspend fun getProfile(@Header("Authorization") token: String): UserDto
}