package com.example.cinema.data.repository


import com.example.cinema.data.remote.MovieApi
import com.example.cinema.data.remote.dto.AuthTokenPairDto
import com.example.cinema.data.remote.dto.MovieDto
import com.example.cinema.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api: MovieApi
): MovieRepository {

    override suspend fun getMovies(token: AuthTokenPairDto, filter: String): List<MovieDto> {
        return api.getMovies("Bearer ${token.accessToken}", filter)
    }
}