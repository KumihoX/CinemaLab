package com.example.cinema.data.storage

import com.example.cinema.data.remote.api.dto.CollectionListItemDto

interface FavoritesStorage {
    companion object {
        const val FAVORITE_COLLECTION_ID = "favoriteCollectionId"
        const val FAVORITE_COLLECTION_NAME = "favoriteCollectionName"
    }

    fun saveFavoriteCollection(collection: CollectionListItemDto)

    fun getFavoriteCollection(): CollectionListItemDto
}