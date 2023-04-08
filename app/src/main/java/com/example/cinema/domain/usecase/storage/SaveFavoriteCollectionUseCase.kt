package com.example.cinema.domain.usecase.storage

import android.content.Context
import com.example.cinema.data.remote.dto.CollectionListItemDto
import com.example.cinema.data.repository.FavoriteRepositoryImpl

class SaveFavoriteCollectionUseCase(private val context: Context) {
    private val favoriteRepository by lazy {
        FavoriteRepositoryImpl(context)
    }

    fun execute(collection: CollectionListItemDto) {
        favoriteRepository.saveFavoriteCollectionToLocalStorage(collection)
    }
}