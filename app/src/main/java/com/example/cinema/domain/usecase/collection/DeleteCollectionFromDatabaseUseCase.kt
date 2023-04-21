package com.example.cinema.domain.usecase.collection

import com.example.cinema.data.remote.database.CollectionDao
import javax.inject.Inject

class DeleteCollectionFromDatabaseUseCase @Inject constructor(
    private val database: CollectionDao
) {
    operator fun invoke(
        collectionId: String
    ) {
        return database.deleteCollection(collectionId)
    }
}