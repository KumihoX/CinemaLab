package com.example.cinema.presentation.main

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinema.data.remote.dto.MovieDto
import com.example.cinema.data.remote.dto.toMovie
import com.example.cinema.domain.model.Movie
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
    private val getMoviesUseCase: GetMoviesUseCase
) : ViewModel() {
    private val _cover = MutableLiveData("")
    val cover: LiveData<String> = _cover

    private val _inTrendsList: MutableLiveData<List<Movie>> = MutableLiveData(emptyList())
    val inTrendsList: LiveData<List<Movie>> = _inTrendsList

    private val _youWatchedCover = MutableLiveData("")
    val youWatchedCover: LiveData<String> = _youWatchedCover

    private val _youWatchedText = MutableLiveData("")
    val youWatchedText: LiveData<String> = _youWatchedText

    private val _newList: MutableLiveData<List<Movie>> = MutableLiveData(emptyList())
    val newList: LiveData<List<Movie>> = _newList

    private val _forYouList: MutableLiveData<List<Movie>> = MutableLiveData(emptyList())
    val forYouList: LiveData<List<Movie>> = _forYouList

    fun getMainScreenInfo() {
        getCover()
        getInTrendsList()
        getYouWatched()
        getNewList()
        getForYouList()
    }

    private fun getCover() {
        viewModelScope.launch {
            try {
                val coverImage = getCoverUseCase(context)
                _cover.value = coverImage.backgroundImage
            } catch (rethrow: CancellationException) {
                throw rethrow
            } catch (ex: Exception) {

            }
        }
    }

    private fun getInTrendsList() {
        viewModelScope.launch {
            try {
                val inTrendData = getMoviesUseCase(context, "inTrend")
                _inTrendsList.value = inTrendData.map { it.toMovie() }
            } catch (rethrow: CancellationException) {
                throw rethrow
            } catch (ex: Exception) {

            }
        }
    }

    private fun getYouWatched() {

        viewModelScope.launch {
            try {
                val youWatchedData = getMoviesUseCase(context, "lastView")
                if (youWatchedData.isNotEmpty()) {
                    _youWatchedCover.value = youWatchedData[0].poster
                    _youWatchedText.value = youWatchedData[0].name
                }
            } catch (rethrow: CancellationException) {
                throw rethrow
            } catch (ex: Exception) {

            }
        }
    }

    private fun getNewList() {
        viewModelScope.launch {
            try {
                val newData = getMoviesUseCase(context, "new")
                _newList.value = newData.map { it.toMovie() }
            } catch (rethrow: CancellationException) {
                throw rethrow
            } catch (ex: Exception) {

            }
        }
    }

    private fun getForYouList() {
        viewModelScope.launch {
            try {
                val forMeData = getMoviesUseCase(context, "forMe")
                _forYouList.value = forMeData.map { it.toMovie() }
            } catch (rethrow: CancellationException) {
                throw rethrow
            } catch (ex: Exception) {

            }
        }
    }
}