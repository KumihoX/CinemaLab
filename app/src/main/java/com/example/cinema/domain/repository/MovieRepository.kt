package com.example.cinema.domain.repository

import com.example.cinema.data.remote.dto.AuthTokenPairDto
import com.example.cinema.data.remote.dto.EpisodeDto
import com.example.cinema.data.remote.dto.MovieDto

interface MovieRepository {
    suspend fun getMovies(token: AuthTokenPairDto, filter: String): List<MovieDto>

    suspend fun getMovieEpisodes(token: AuthTokenPairDto, movieId: String): List<EpisodeDto>
}