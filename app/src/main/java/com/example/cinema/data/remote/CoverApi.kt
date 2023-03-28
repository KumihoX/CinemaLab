package com.example.cinema.data.remote

import com.example.cinema.data.remote.dto.CoverMovieDto
import retrofit2.http.GET
import retrofit2.http.Header

interface CoverApi {

    @GET("cover")
    suspend fun getCover(@Header("Authorization") token: String): CoverMovieDto

}