package com.example.cinema.data.remote.api

import com.example.cinema.data.remote.api.dto.EpisodeDto
import com.example.cinema.data.remote.api.dto.MovieDto
import retrofit2.Response
import retrofit2.http.*

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

    @POST("movies/{movieId}/dislike")
    suspend fun postMovieDislike(
        @Header("Authorization") token: String,
        @Path("movieId") movieId: String
    ): Response<Void>
}