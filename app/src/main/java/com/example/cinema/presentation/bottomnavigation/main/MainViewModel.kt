package com.example.cinema.presentation.bottomnavigation.main

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinema.R
import com.example.cinema.data.remote.api.dto.toMovie
import com.example.cinema.domain.model.Movie
import com.example.cinema.domain.usecase.history.GetHistoryUseCase
import com.example.cinema.domain.usecase.main.GetCoverUseCase
import com.example.cinema.domain.usecase.main.GetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val getCoverUseCase: GetCoverUseCase,
    private val getMoviesUseCase: GetMoviesUseCase,
    private val getHistoryUseCase: GetHistoryUseCase
) : ViewModel() {
    sealed class MainState {
        object Loading : MainState()
        class Success(
            val cover: String,
            val inTrend: List<Movie>,
            val youWatchedCover: List<Movie>,
            val newList: List<Movie>,
            val forYou: List<Movie>
        ) : MainState()

        class Failure(val errorMessage: String) : MainState()
    }

    private val _state = MutableLiveData<MainState>(MainState.Loading)
    val state: LiveData<MainState> = _state

    fun getMainScreenInfo() {
        _state.value = MainState.Loading
        viewModelScope.launch {
            try {
                val coverImage = getCoverUseCase(context)
                val inTrendData = getMoviesUseCase(context, context.getString(R.string.filter_in_trend))
                val youWatchedData = getMoviesUseCase(context, context.getString(R.string.filter_last_view))
                val newData = getMoviesUseCase(context, context.getString(R.string.filter_new))
                val forMeData = getMoviesUseCase(context, context.getString(R.string.filter_for_me))
                val history = getHistoryUseCase(context)

                var youWatchedList = youWatchedData.map { it.toMovie() }
                youWatchedList[0].episodeId = history[0].episodeId

                _state.value = MainState.Success(
                    coverImage.backgroundImage,
                    inTrendData.map { it.toMovie() },
                    youWatchedList,
                    newData.map { it.toMovie() },
                    forMeData.map { it.toMovie() }
                )
            } catch (rethrow: CancellationException) {
                throw rethrow
            } catch (ex: Exception) {
                _state.value = MainState.Failure(ex.message.toString())
            }
        }
    }
}