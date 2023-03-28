package com.example.cinema.data.repository

import com.example.cinema.data.remote.CoverApi
import com.example.cinema.data.remote.dto.AuthTokenPairDto
import com.example.cinema.data.remote.dto.CoverMovieDto
import com.example.cinema.domain.repository.CoverRepository
import javax.inject.Inject

class CoverRepositoryImpl @Inject constructor(
    private val api: CoverApi
): CoverRepository {
    override suspend fun getCover(token: AuthTokenPairDto): CoverMovieDto {
        return api.getCover("Bearer ${token.accessToken}")
    }
}