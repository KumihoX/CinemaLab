package com.example.cinema.domain.usecase.compilation

import android.content.Context
import com.example.cinema.domain.repository.MovieRepository
import com.example.cinema.domain.usecase.storage.GetTokenFromLocalStorageUseCase
import retrofit2.Response
import javax.inject.Inject

class DislikeMovieUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(context: Context, movieId: String): Response<Void> {
        val getTokenFromLocalStorageUseCase = GetTokenFromLocalStorageUseCase(context)
        val token = getTokenFromLocalStorageUseCase.execute()

        return repository.postMovieDislike(token, movieId)
    }
}