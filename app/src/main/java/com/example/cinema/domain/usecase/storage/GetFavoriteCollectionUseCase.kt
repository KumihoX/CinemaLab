package com.example.cinema.domain.usecase.storage

import android.content.Context
import com.example.cinema.data.remote.dto.CollectionListItemDto
import com.example.cinema.data.repository.FavoriteRepositoryImpl

class GetFavoriteCollectionUseCase(
    private val context: Context
) {
    private val favoriteRepositoryImpl by lazy {
        FavoriteRepositoryImpl(context)
    }

    fun execute(): CollectionListItemDto {
        return favoriteRepositoryImpl.getFavoriteCollectionFromLocalStorage()
    }
}