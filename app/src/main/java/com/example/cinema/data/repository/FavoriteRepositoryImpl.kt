package com.example.cinema.data.repository

import android.content.Context
import com.example.cinema.data.remote.api.dto.CollectionListItemDto
import com.example.cinema.data.storage.SharedPreferencesStorage
import com.example.cinema.domain.repository.FavoriteRepository

class FavoriteRepositoryImpl(context: Context) : FavoriteRepository {
    private val sharedPreferencesStorage by lazy {
        SharedPreferencesStorage(context)
    }

    override fun saveFavoriteCollectionToLocalStorage(collection: CollectionListItemDto) {
        sharedPreferencesStorage.saveFavoriteCollection(collection)
    }

    override fun getFavoriteCollectionFromLocalStorage(): CollectionListItemDto {
        return sharedPreferencesStorage.getFavoriteCollection()
    }
}