package com.example.cinema.data.remote.api

import com.example.cinema.data.remote.api.dto.CoverMovieDto
import retrofit2.http.GET
import retrofit2.http.Header

interface CoverApi {

    @GET("cover")
    suspend fun getCover(@Header("Authorization") token: String): CoverMovieDto

}