package com.example.cinema.domain.repository

import com.example.cinema.data.remote.api.dto.AuthTokenPairDto
import com.example.cinema.data.remote.api.dto.EpisodeDto
import com.example.cinema.data.remote.api.dto.MovieDto
import retrofit2.Response

interface MovieRepository {
    suspend fun getMovies(token: AuthTokenPairDto, filter: String): List<MovieDto>

    suspend fun getMovieEpisodes(token: AuthTokenPairDto, movieId: String): List<EpisodeDto>

    suspend fun postMovieDislike(token: AuthTokenPairDto, movieId: String): Response<Void>
}