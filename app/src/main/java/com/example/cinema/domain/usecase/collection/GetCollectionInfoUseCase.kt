package com.example.cinema.domain.usecase.collection

import android.content.Context
import com.example.cinema.data.remote.dto.MovieDto
import com.example.cinema.domain.repository.CollectionRepository
import com.example.cinema.domain.usecase.token.GetTokenFromLocalStorageUseCase
import javax.inject.Inject

class GetCollectionInfoUseCase @Inject constructor(
    private val repository: CollectionRepository
) {
    suspend operator fun invoke(context: Context, collectionId: String): List<MovieDto> {
        val getTokenFromLocalStorageUseCase = GetTokenFromLocalStorageUseCase(context)
        val token = getTokenFromLocalStorageUseCase.execute()

        return repository.getCollectionInfo(token, collectionId)
    }
}