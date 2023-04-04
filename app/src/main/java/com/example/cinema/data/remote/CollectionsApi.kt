package com.example.cinema.data.remote

import com.example.cinema.data.remote.dto.CollectionFormDto
import com.example.cinema.data.remote.dto.CollectionListItemDto
import com.example.cinema.data.remote.dto.MovieDto
import retrofit2.http.*

interface CollectionsApi {
    @GET("collections")
    suspend fun getCollections(@Header("Authorization") token: String): List<CollectionListItemDto>

    @POST("collections")
    suspend fun postCollections(
        @Header("Authorization") token: String,
        @Body collectionForm: CollectionFormDto
    )

    @DELETE("collections/{collectionId}")
    suspend fun deleteCollection(
        @Header("Authorization") token: String,
        @Path("collectionId") collectionId: String
    )

    @GET("collections/{collectionId}/movies")
    suspend fun getCollectionInfo(
        @Header("Authorization") token: String,
        @Path("collectionId") collectionId: String
    ) : List<MovieDto>
}