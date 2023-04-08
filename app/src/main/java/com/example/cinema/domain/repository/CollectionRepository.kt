package com.example.cinema.domain.repository

import com.example.cinema.data.remote.dto.*
import retrofit2.Response

interface CollectionRepository {
    suspend fun getCollections(token: AuthTokenPairDto): List<CollectionListItemDto>

    suspend fun postCollections(
        token: AuthTokenPairDto,
        collectionForm: CollectionFormDto
    ): CollectionListItemDto

    suspend fun deleteCollection(token: AuthTokenPairDto, collectionId: String): Response<Void>

    suspend fun getCollectionInfo(token: AuthTokenPairDto, collectionId: String): List<MovieDto>

    suspend fun postMovieInCollection(
        token: AuthTokenPairDto,
        collectionId: String,
        movieValue: MovieValueDto
    ): Response<Void>
}