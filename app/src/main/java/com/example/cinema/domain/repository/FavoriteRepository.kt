package com.example.cinema.domain.repository

import com.example.cinema.data.remote.api.dto.CollectionListItemDto

interface FavoriteRepository {
    fun saveFavoriteCollectionToLocalStorage(collection: CollectionListItemDto)

    fun getFavoriteCollectionFromLocalStorage(): CollectionListItemDto

    fun deleteFavoriteCollectionFromLocalStorage()
}