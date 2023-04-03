package com.example.cinema.domain.repository

import com.example.cinema.data.remote.dto.AuthTokenPairDto
import com.example.cinema.data.remote.dto.MovieDto

interface MovieRepository {
    suspend fun getMovies(token: AuthTokenPairDto, filter: String): List<MovieDto>
}