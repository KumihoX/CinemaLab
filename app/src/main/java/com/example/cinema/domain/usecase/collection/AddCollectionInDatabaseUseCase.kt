package com.example.cinema.domain.usecase.collection

import com.example.cinema.data.remote.database.CollectionDao
import com.example.cinema.data.remote.database.entity.CollectionEntity
import javax.inject.Inject

class AddCollectionInDatabaseUseCase @Inject constructor(
    private val database: CollectionDao
) {
    operator fun invoke(
        collection: CollectionEntity
    ) {
        return database.insertCollection(collection)
    }
}