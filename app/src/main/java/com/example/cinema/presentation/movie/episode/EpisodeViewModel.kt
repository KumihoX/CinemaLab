package com.example.cinema.presentation.movie.episode

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinema.data.remote.dto.MovieValueDto
import com.example.cinema.domain.usecase.collection.PostMovieInCollectionUseCase
import com.example.cinema.domain.usecase.storage.GetFavoriteCollectionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodeViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val postMovieInCollectionUseCase: PostMovieInCollectionUseCase
) : ViewModel() {
    private val favoriteCollectionId = GetFavoriteCollectionUseCase(context).execute().collectionId


    sealed class EpisodeState {
        object Loading : EpisodeState()
        object Success : EpisodeState()
        class Failure(val errorMessage: String) : EpisodeState()
    }

    private val _state = MutableLiveData<EpisodeState>(EpisodeState.Success)
    val state: LiveData<EpisodeState> = _state

    fun addMovieInFavorites(movieId: String) {
        _state.value = EpisodeState.Loading
        viewModelScope.launch {
            try {
                val movieValue = MovieValueDto(movieId)
                postMovieInCollectionUseCase(context, favoriteCollectionId, movieValue)
                _state.value = EpisodeState.Success
            } catch (rethrow: CancellationException) {
                throw rethrow
            } catch (ex: Exception) {
                if (ex.message == "HTTP 409 Conflict") {
                    _state.value =
                        EpisodeState.Failure("Этот фильм уже был добавлен вами в \"Избранное\"")
                }
                else{
                    _state.value = EpisodeState.Failure(ex.message.toString())
                }
            }
        }
    }
}