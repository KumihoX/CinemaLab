package com.example.cinema.domain.usecase.collection

import android.content.Context
import com.example.cinema.data.remote.api.dto.MovieValueDto
import com.example.cinema.domain.repository.CollectionRepository
import com.example.cinema.domain.usecase.storage.GetTokenFromLocalStorageUseCase
import javax.inject.Inject

class PostMovieInCollectionUseCase @Inject constructor(
    private val repository: CollectionRepository
) {
    suspend operator fun invoke(
        context: Context,
        collectionId: String,
        movieValueDto: MovieValueDto
    ) {
        val getTokenFromLocalStorageUseCase = GetTokenFromLocalStorageUseCase(context)
        val token = getTokenFromLocalStorageUseCase.execute()

        return repository.postMovieInCollection(token, collectionId, movieValueDto)
    }
}