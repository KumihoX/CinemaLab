package com.example.cinema.presentation.movie.moviedetail

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinema.data.remote.api.dto.EpisodeDto
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
            val episodes: List<EpisodeDto>,
            val interval: String
        ) : MovieDetailState()

        class Failure(val errorMessage: String) : MovieDetailState()
    }

    private val _state = MutableLiveData<MovieDetailState>(MovieDetailState.Loading)
    val state: LiveData<MovieDetailState> = _state

    fun getEpisodeInterval(listEpisodes: List<EpisodeDto>): String {
        var min = "10000"
        var max = "0"
        for (i in listEpisodes.indices) {
            if (listEpisodes[i].year.toInt() < min.toInt()) {
                min = listEpisodes[i].year
            }
            if (listEpisodes[i].year.toInt() > max.toInt()) {
                max = listEpisodes[i].year
            }
        }
        if (min.toInt() == max.toInt()) {
            return min
        }
        return "$min - $max"
    }

    fun getMovieInfo(movieInfo: Movie) {
        _state.value = MovieDetailState.Loading
        viewModelScope.launch {
            try {
                val episodesList = getMovieEpisodesUseCase(context, movieInfo.movieId)
                val interval = getEpisodeInterval(episodesList)
                _state.value = MovieDetailState.Success(movieInfo, episodesList, interval)
            } catch (rethrow: CancellationException) {
                throw rethrow
            } catch (ex: Exception) {
                _state.value = MovieDetailState.Failure(ex.message.toString())
            }
        }

    }
}