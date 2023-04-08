package com.example.cinema.domain.repository

import com.example.cinema.data.remote.dto.CollectionListItemDto

interface FavoriteRepository {
    fun saveFavoriteCollectionToLocalStorage(collection: CollectionListItemDto)

    fun getFavoriteCollectionFromLocalStorage(): CollectionListItemDto
}