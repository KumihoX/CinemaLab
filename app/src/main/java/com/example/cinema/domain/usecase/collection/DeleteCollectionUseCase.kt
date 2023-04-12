package com.example.cinema.domain.usecase.collection

import android.content.Context
import com.example.cinema.data.remote.database.CollectionDao
import com.example.cinema.domain.repository.CollectionRepository
import com.example.cinema.domain.usecase.storage.GetTokenFromLocalStorageUseCase
import javax.inject.Inject

class DeleteCollectionUseCase @Inject constructor(
    private val repository: CollectionRepository
) {
    suspend operator fun invoke(context: Context, collectionId: String) {
        val getTokenFromLocalStorageUseCase = GetTokenFromLocalStorageUseCase(context)
        val token = getTokenFromLocalStorageUseCase.execute()

        repository.deleteCollection(token, collectionId)
    }
}