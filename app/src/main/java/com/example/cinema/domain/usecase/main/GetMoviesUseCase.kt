package com.example.cinema.domain.usecase.main

import android.content.Context
import com.example.cinema.data.remote.dto.MovieDto
import com.example.cinema.domain.repository.MovieRepository
import com.example.cinema.domain.usecase.storage.GetTokenFromLocalStorageUseCase
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(context: Context, filter: String): List<MovieDto> {
        val getTokenFromLocalStorageUseCase = GetTokenFromLocalStorageUseCase(context)
        val token = getTokenFromLocalStorageUseCase.execute()

        return repository.getMovies(token, filter)
    }
}