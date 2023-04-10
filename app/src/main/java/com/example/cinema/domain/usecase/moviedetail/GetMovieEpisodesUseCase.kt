package com.example.cinema.domain.usecase.moviedetail

import android.content.Context
import com.example.cinema.data.remote.dto.EpisodeDto
import com.example.cinema.domain.repository.MovieRepository
import com.example.cinema.domain.usecase.storage.GetTokenFromLocalStorageUseCase
import javax.inject.Inject

class GetMovieEpisodesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(context: Context, movieId: String): List<EpisodeDto> {
        val getTokenFromLocalStorageUseCase = GetTokenFromLocalStorageUseCase(context)
        val token = getTokenFromLocalStorageUseCase.execute()

        return repository.getMovieEpisodes(token, movieId)
    }
}