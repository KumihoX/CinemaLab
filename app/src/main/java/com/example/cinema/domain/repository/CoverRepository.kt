package com.example.cinema.domain.repository

import com.example.cinema.data.remote.dto.AuthTokenPairDto
import com.example.cinema.data.remote.dto.CoverMovieDto

interface CoverRepository {
    suspend fun getCover(token: AuthTokenPairDto): CoverMovieDto
}