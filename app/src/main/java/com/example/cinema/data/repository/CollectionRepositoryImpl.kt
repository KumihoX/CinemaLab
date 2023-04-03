package com.example.cinema.data.repository

import com.example.cinema.data.remote.CollectionsApi
import com.example.cinema.data.remote.dto.AuthTokenPairDto
import com.example.cinema.data.remote.dto.CollectionFormDto
import com.example.cinema.data.remote.dto.CollectionListItemDto
import com.example.cinema.domain.repository.CollectionRepository
import retrofit2.http.Body
import retrofit2.http.Header
import javax.inject.Inject

class CollectionRepositoryImpl @Inject constructor(
    private val api: CollectionsApi
): CollectionRepository {
    override suspend fun getCollections(token: AuthTokenPairDto): List<CollectionListItemDto> {
        return api.getCollections("Bearer ${token.accessToken}")
    }

    override suspend fun postCollections(token: AuthTokenPairDto, collectionForm: CollectionFormDto) {
        return api.postCollections("Bearer ${token.accessToken}", collectionForm)
    }
}