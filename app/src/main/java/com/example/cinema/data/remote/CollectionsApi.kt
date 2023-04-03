package com.example.cinema.data.remote

import com.example.cinema.data.remote.dto.CollectionFormDto
import com.example.cinema.data.remote.dto.CollectionListItemDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface CollectionsApi {
    @GET("collections")
    suspend fun getCollections(@Header("Authorization") token: String): List<CollectionListItemDto>

    @POST("collections")
    suspend fun postCollections(@Header("Authorization") token: String, @Body collectionForm: CollectionFormDto )
}