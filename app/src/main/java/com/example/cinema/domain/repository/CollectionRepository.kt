package com.example.cinema.domain.repository

import com.example.cinema.data.remote.dto.AuthTokenPairDto
import com.example.cinema.data.remote.dto.CollectionFormDto
import com.example.cinema.data.remote.dto.CollectionListItemDto
import com.example.cinema.data.remote.dto.MovieDto
import retrofit2.Response

interface CollectionRepository {
    suspend fun getCollections(token: AuthTokenPairDto): List<CollectionListItemDto>

    suspend fun postCollections(
        token: AuthTokenPairDto,
        collectionForm: CollectionFormDto
    ): CollectionListItemDto

    suspend fun deleteCollection(token: AuthTokenPairDto, collectionId: String): Response<Void>

    suspend fun getCollectionInfo(token: AuthTokenPairDto, collectionId: String): List<MovieDto>
}