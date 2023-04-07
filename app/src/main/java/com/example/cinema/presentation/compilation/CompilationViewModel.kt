package com.example.cinema.presentation.compilation

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinema.data.remote.dto.AuthTokenPairDto
import com.example.cinema.data.remote.dto.MovieDto
import com.example.cinema.domain.usecase.main.GetMoviesUseCase
import com.example.cinema.domain.usecase.validation.SignInValidationForm
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompilationViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val getMoviesUseCase: GetMoviesUseCase,
) : ViewModel() {

    sealed class CompilationState {
        object Loading : CompilationState()
        class Failure(val errorMessage: String) : CompilationState()
        class Success(val compilation: List<MovieDto>) : CompilationState()
    }

    private val _state = MutableLiveData<CompilationState>(CompilationState.Loading)
    val state: LiveData<CompilationState> = _state

    private val _compilationList: MutableLiveData<List<MovieDto>> = MutableLiveData(emptyList())
    val compilationList: LiveData<List<MovieDto>> = _compilationList

    fun getCompilation(){
        _state.value = CompilationState.Loading
        viewModelScope.launch {
            try {
                val compilation = getMoviesUseCase(context = context, "compilation")
                _state.value = CompilationState.Success(compilation)
            } catch (rethrow: CancellationException) {
                throw rethrow
            } catch (ex: Exception) {
                _state.value = CompilationState.Failure(ex.message.toString())
            }
        }
    }

    fun dislike(movieId: String) {

    }

    fun like(movieId: String) {

    }
}