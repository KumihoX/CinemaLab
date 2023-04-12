package com.example.cinema.domain.usecase.collection

import com.example.cinema.data.remote.database.CollectionDao
import javax.inject.Inject

class DeleteAllCollectionsUseCase @Inject constructor(
    private val database: CollectionDao
) {
    operator fun invoke() {
        return database.deleteAllCollections()
    }
}