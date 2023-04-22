package com.example.cinema.data.repository

import com.example.cinema.data.remote.api.CoverApi
import com.example.cinema.data.remote.api.dto.AuthTokenPairDto
import com.example.cinema.data.remote.api.dto.CoverMovieDto
import com.example.cinema.domain.repository.CoverRepository
import javax.inject.Inject

class CoverRepositoryImpl @Inject constructor(
    private val api: CoverApi
) : CoverRepository {
    override suspend fun getCover(token: AuthTokenPairDto): CoverMovieDto {
        return api.getCover("Bearer ${token.accessToken}")
    }
}