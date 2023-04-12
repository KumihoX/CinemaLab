package com.example.cinema.data.remote.api

import com.example.cinema.data.remote.api.dto.AuthCredentialDto
import com.example.cinema.data.remote.api.dto.AuthTokenPairDto
import com.example.cinema.data.remote.api.dto.RegistrationBodyDto
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("auth/register")
    suspend fun register(@Body body: RegistrationBodyDto): AuthTokenPairDto

    @POST("auth/login")
    suspend fun comeIn(@Body body: AuthCredentialDto): AuthTokenPairDto

}