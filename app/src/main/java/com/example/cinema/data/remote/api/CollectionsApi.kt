package com.example.cinema.data.remote.api

import com.example.cinema.data.remote.api.dto.CollectionFormDto
import com.example.cinema.data.remote.api.dto.CollectionListItemDto
import com.example.cinema.data.remote.api.dto.MovieDto
import com.example.cinema.data.remote.api.dto.MovieValueDto
import retrofit2.Response
import retrofit2.http.*

interface CollectionsApi {
    @GET("collections")
    suspend fun getCollections(@Header("Authorization") token: String): List<CollectionListItemDto>

    @POST("collections")
    suspend fun postCollections(
        @Header("Authorization") token: String,
        @Body collectionForm: CollectionFormDto
    ): CollectionListItemDto

    @DELETE("collections/{collectionId}")
    suspend fun deleteCollection(
        @Header("Authorization") token: String,
        @Path("collectionId") collectionId: String
    ): Response<Void>

    @GET("collections/{collectionId}/movies")
    suspend fun getCollectionInfo(
        @Header("Authorization") token: String,
        @Path("collectionId") collectionId: String
    ): List<MovieDto>

    @POST("collections/{collectionId}/movies")
    suspend fun postMovieInCollection(
        @Header("Authorization") token: String,
        @Path("collectionId") collectionId: String,
        @Body movieValue: MovieValueDto
    )
}