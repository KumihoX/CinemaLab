package com.example.cinema.presentation.bottomnavigation.compilation

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinema.R
import com.example.cinema.data.remote.api.dto.MovieDto
import com.example.cinema.data.remote.api.dto.MovieValueDto
import com.example.cinema.domain.usecase.collection.PostMovieInCollectionUseCase
import com.example.cinema.domain.usecase.compilation.DislikeMovieUseCase
import com.example.cinema.domain.usecase.main.GetMoviesUseCase
import com.example.cinema.domain.usecase.storage.GetFavoriteCollectionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompilationViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val getMoviesUseCase: GetMoviesUseCase,
    private val dislikeMovieUseCase: DislikeMovieUseCase,
    private val postMovieInCollectionUseCase: PostMovieInCollectionUseCase
) : ViewModel() {

    sealed class CompilationState {
        object FirstLoading : CompilationState()
        object Loading : CompilationState()
        object Continue : CompilationState()
        class Failure(val errorMessage: String) : CompilationState()
        class Success(val compilation: List<MovieDto>) : CompilationState()
    }

    private val favoriteCollectionId = GetFavoriteCollectionUseCase(context).execute().collectionId

    private val _state = MutableLiveData<CompilationState>(CompilationState.FirstLoading)
    val state: LiveData<CompilationState> = _state

    fun getCompilation() {
        _state.value = CompilationState.FirstLoading
        viewModelScope.launch {
            try {
                val compilation = getMoviesUseCase(
                    context = context,
                    context.getString(R.string.filter_compilation)
                )
                _state.value = CompilationState.Success(compilation)
            } catch (rethrow: CancellationException) {
                throw rethrow
            } catch (ex: Exception) {
                _state.value = CompilationState.Failure(ex.message.toString())
            }
        }
    }

    fun dislike(movieId: String) {
        _state.value = CompilationState.Loading
        viewModelScope.launch {
            try {
                dislikeMovieUseCase(context, movieId)
                _state.value = CompilationState.Continue
            } catch (rethrow: CancellationException) {
                throw rethrow
            } catch (ex: Exception) {
                _state.value = CompilationState.Failure(ex.message.toString())
            }
        }
    }

    fun like(movieId: String) {
        _state.value = CompilationState.Loading
        viewModelScope.launch {
            try {
                val movieValue = MovieValueDto(movieId)
                postMovieInCollectionUseCase(context, favoriteCollectionId, movieValue)
                _state.value = CompilationState.Continue
            } catch (rethrow: CancellationException) {
                throw rethrow
            } catch (ex: Exception) {
                if (ex.message == "HTTP 409 Conflict") {
                    _state.value =
                        CompilationState.Failure(context.getString(R.string.error_this_film_in_favorites_now))
                } else {
                    _state.value = CompilationState.Failure(ex.message.toString())
                }
            }
        }
    }
}