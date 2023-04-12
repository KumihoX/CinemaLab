package com.example.cinema.domain.usecase.collection

import android.content.Context
import com.example.cinema.data.remote.api.dto.CollectionFormDto
import com.example.cinema.data.remote.api.dto.CollectionListItemDto
import com.example.cinema.data.remote.database.CollectionDao
import com.example.cinema.domain.repository.CollectionRepository
import com.example.cinema.domain.usecase.storage.GetTokenFromLocalStorageUseCase
import javax.inject.Inject

class PostCollectionUseCase @Inject constructor(
    private val repository: CollectionRepository
) {
    suspend operator fun invoke(
        context: Context,
        collectionForm: CollectionFormDto
    ): CollectionListItemDto {
        val getTokenFromLocalStorageUseCase = GetTokenFromLocalStorageUseCase(context)
        val token = getTokenFromLocalStorageUseCase.execute()

        return repository.postCollections(token, collectionForm)
    }
}