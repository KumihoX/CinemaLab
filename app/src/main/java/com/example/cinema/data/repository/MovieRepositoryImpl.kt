package com.example.cinema.data.repository


import com.example.cinema.data.remote.MovieApi
import com.example.cinema.data.remote.dto.AuthTokenPairDto
import com.example.cinema.data.remote.dto.EpisodeDto
import com.example.cinema.data.remote.dto.MovieDto
import com.example.cinema.domain.repository.MovieRepository
import retrofit2.Response
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api: MovieApi
) : MovieRepository {

    override suspend fun getMovies(token: AuthTokenPairDto, filter: String): List<MovieDto> {
        return api.getMovies("Bearer ${token.accessToken}", filter)
    }

    override suspend fun getMovieEpisodes(
        token: AuthTokenPairDto,
        movieId: String
    ): List<EpisodeDto> {
        return api.getMovieEpisodes("Bearer ${token.accessToken}", movieId)
    }

    override suspend fun postMovieDislike(
        token: AuthTokenPairDto,
        movieId: String
    ): Response<Void> {
        return api.postMovieDislike("Bearer ${token.accessToken}", movieId)
    }
}