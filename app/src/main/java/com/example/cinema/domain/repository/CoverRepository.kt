package com.example.cinema.domain.repository

import com.example.cinema.data.remote.api.dto.AuthTokenPairDto
import com.example.cinema.data.remote.api.dto.CoverMovieDto

interface CoverRepository {
    suspend fun getCover(token: AuthTokenPairDto): CoverMovieDto
}