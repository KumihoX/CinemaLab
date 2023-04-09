package com.example.cinema.presentation.movie.moviedetail

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinema.data.remote.dto.EpisodeDto
import com.example.cinema.domain.model.Movie
import com.example.cinema.domain.usecase.moviedetail.GetMovieEpisodesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val getMovieEpisodesUseCase: GetMovieEpisodesUseCase
) : ViewModel() {
    sealed class MovieDetailState {
        object Loading : MovieDetailState()
        class Success(
            val movieInfo: Movie,
            val episodes: List<EpisodeDto>
        ) : MovieDetailState()

        class Failure(val errorMessage: String) : MovieDetailState()
    }

    private val _state = MutableLiveData<MovieDetailState>(MovieDetailState.Loading)
    val state: LiveData<MovieDetailState> = _state

    fun getMovieInfo(movieInfo: Movie) {
        _state.value = MovieDetailState.Loading
        viewModelScope.launch {
            try {
                val episodesList = getMovieEpisodesUseCase(context, movieInfo.movieId)
                _state.value = MovieDetailState.Success(movieInfo, episodesList)
            } catch (rethrow: CancellationException) {
                throw rethrow
            } catch (ex: Exception) {
                _state.value = MovieDetailState.Failure(ex.message.toString())
            }
        }

    }
}