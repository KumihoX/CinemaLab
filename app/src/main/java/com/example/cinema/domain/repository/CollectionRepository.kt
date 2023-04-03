package com.example.cinema.domain.repository

import com.example.cinema.data.remote.dto.AuthTokenPairDto
import com.example.cinema.data.remote.dto.CollectionFormDto
import com.example.cinema.data.remote.dto.CollectionListItemDto

interface CollectionRepository {
    suspend fun getCollections(token: AuthTokenPairDto): List<CollectionListItemDto>

    suspend fun postCollections(token: AuthTokenPairDto, collectionForm: CollectionFormDto)
}