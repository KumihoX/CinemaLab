package com.example.cinema.domain.usecase.collection

import com.example.cinema.data.remote.database.CollectionDao
import javax.inject.Inject

class EditCollectionInDatabaseUseCase @Inject constructor(
    private val database: CollectionDao
) {
    operator fun invoke(imageId: Int, name: String, id: String) {
        return database.changeCollection(imageId, name, id)
    }
}