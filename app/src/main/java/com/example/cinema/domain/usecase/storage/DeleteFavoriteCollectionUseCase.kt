package com.example.cinema.domain.usecase.storage

import android.content.Context
import com.example.cinema.data.repository.FavoriteRepositoryImpl

class DeleteFavoriteCollectionUseCase(private val context: Context) {
    private val favoriteRepository by lazy {
        FavoriteRepositoryImpl(context)
    }

    fun execute() {
        favoriteRepository.deleteFavoriteCollectionFromLocalStorage()
    }
}