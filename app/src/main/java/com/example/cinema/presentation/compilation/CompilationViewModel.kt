package com.example.cinema.presentation.compilation

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinema.data.remote.dto.MovieDto
import com.example.cinema.domain.usecase.main.GetMoviesUseCase
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

    private val _compilationList: MutableLiveData<List<MovieDto>> = MutableLiveData(emptyList())
    val compilationList: LiveData<List<MovieDto>> = _compilationList

    fun getCompilation(){
        viewModelScope.launch {
            try {
                val compilations = getMoviesUseCase(context = context, "compilation")
                _compilationList.value = compilations
            } catch (rethrow: CancellationException) {
                throw rethrow
            } catch (ex: Exception) {

            }
        }
    }
}