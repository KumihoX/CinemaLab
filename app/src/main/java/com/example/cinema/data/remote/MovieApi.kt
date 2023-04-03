package com.example.cinema.data.remote

import com.example.cinema.data.remote.dto.EpisodeDto
import com.example.cinema.data.remote.dto.MovieDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET("movies")
    suspend fun getMovies(
        @Header("Authorization") token: String,
        @Query("filter") filter: String
    ): List<MovieDto>

    @GET("movies/{movieId}/episodes")
    suspend fun getMovieEpisodes(
        @Header("Authorization") token: String,
        @Path("movieId") movieId: String
    ): List<EpisodeDto>
}