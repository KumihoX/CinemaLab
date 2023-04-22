package com.example.cinema.data.repository

import com.example.cinema.data.remote.api.CollectionsApi
import com.example.cinema.data.remote.api.dto.*
import com.example.cinema.domain.repository.CollectionRepository
import retrofit2.Response
import javax.inject.Inject

class CollectionRepositoryImpl @Inject constructor(
    private val api: CollectionsApi
) : CollectionRepository {
    override suspend fun getCollections(token: AuthTokenPairDto): List<CollectionListItemDto> {
        return api.getCollections("Bearer ${token.accessToken}")
    }

    override suspend fun postCollections(
        token: AuthTokenPairDto,
        collectionForm: CollectionFormDto
    ): CollectionListItemDto {
        return api.postCollections("Bearer ${token.accessToken}", collectionForm)
    }

    override suspend fun deleteCollection(
        token: AuthTokenPairDto,
        collectionId: String
    ): Response<Void> {
        return api.deleteCollection("Bearer ${token.accessToken}", collectionId)
    }

    override suspend fun getCollectionInfo(
        token: AuthTokenPairDto,
        collectionId: String
    ): List<MovieDto> {
        return api.getCollectionInfo("Bearer ${token.accessToken}", collectionId)
    }

    override suspend fun postMovieInCollection(
        token: AuthTokenPairDto,
        collectionId: String,
        movieValue: MovieValueDto
    ) {
        return api.postMovieInCollection("Bearer ${token.accessToken}", collectionId, movieValue)
    }
}