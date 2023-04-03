package com.example.cinema.presentation.moviedetail

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinema.data.remote.dto.EpisodeDto
import com.example.cinema.data.remote.dto.MovieDto
import com.example.cinema.data.remote.dto.TagDto
import com.example.cinema.domain.model.AgeEnum
import com.example.cinema.domain.model.Movie
import com.example.cinema.domain.usecase.moviedetail.GetMovieEpisodesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val getMovieEpisodesUseCase: GetMovieEpisodesUseCase
) : ViewModel() {
    private val _poster = MutableLiveData("")
    val poster: LiveData<String> = _poster

    private val _age = MutableLiveData(AgeEnum.Zero)
    val age: LiveData<AgeEnum> = _age

    private val _description = MutableLiveData("")
    val description: LiveData<String> = _description

    private val _frames: MutableLiveData<List<String>> = MutableLiveData(emptyList())
    val frames: LiveData<List<String>> = _frames

    private val _episodes: MutableLiveData<List<EpisodeDto>> = MutableLiveData(emptyList())
    val episodes: LiveData<List<EpisodeDto>> = _episodes

    private val _tags: MutableLiveData<List<TagDto>> = MutableLiveData(emptyList())
    val tags: LiveData<List<TagDto>> = _tags

    fun getMovieInfo (movieInfo: Movie) {
        viewModelScope.launch {
            val episodesList = getMovieEpisodesUseCase(context, movieInfo.movieId)
            _episodes.value = episodesList
        }
        _poster.value = movieInfo.poster
        _age.value = movieInfo.age
        _description.value = movieInfo.description
        _frames.value = movieInfo.imageUrls
        _tags.value = movieInfo.tags
    }
}