package com.example.cinema.data.remote.api

import com.example.cinema.data.remote.api.dto.EpisodeTimeDto
import retrofit2.http.*

interface EpisodesApi {

    @GET("episodes/{episodeId}/time")
    suspend fun getEpisodeTime(
        @Header("Authorization") token: String,
        @Path("episodeId") episodeId: String
    ): EpisodeTimeDto

    @POST("episodes/{episodeId}/time")
    suspend fun postEpisodeTime(
        @Header("Authorization") token: String,
        @Path("episodeId") episodeId: String,
        @Body episodeTime: EpisodeTimeDto
    )
}