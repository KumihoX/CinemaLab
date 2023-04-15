package com.example.cinema.presentation.movie.episode

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinema.data.remote.api.dto.EpisodeTimeDto
import com.example.cinema.data.remote.api.dto.MovieDto
import com.example.cinema.data.remote.api.dto.MovieValueDto
import com.example.cinema.data.remote.database.entity.CollectionEntity
import com.example.cinema.domain.usecase.collection.GetCollectionsFromDatabaseUseCase
import com.example.cinema.domain.usecase.collection.PostMovieInCollectionUseCase
import com.example.cinema.domain.usecase.episodes.GetEpisodeTimeUseCase
import com.example.cinema.domain.usecase.episodes.PostEpisodeTimeUseCase
import com.example.cinema.domain.usecase.storage.GetFavoriteCollectionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodeViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val postMovieInCollectionUseCase: PostMovieInCollectionUseCase,
    private val postEpisodeTimeUseCase: PostEpisodeTimeUseCase,
    private val getEpisodeTimeUseCase: GetEpisodeTimeUseCase,
    private val getCollectionsFromDatabaseUseCase: GetCollectionsFromDatabaseUseCase
) : ViewModel() {
    private val favoriteCollectionId = GetFavoriteCollectionUseCase(context).execute().collectionId

    sealed class EpisodeState {
        object FirstLoading : EpisodeState()
        object Loading : EpisodeState()
        class Initial(val time: Int, val collections: List<CollectionEntity>): EpisodeState()
        object Success : EpisodeState()
        class Failure(val errorMessage: String) : EpisodeState()
    }

    private val _state = MutableLiveData<EpisodeState>(EpisodeState.FirstLoading)
    val state: LiveData<EpisodeState> = _state

    private var collections = emptyList<CollectionEntity>()
    private fun getCollections() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val collectionsListFromDatabase = getCollectionsFromDatabaseUseCase()
                collections = collectionsListFromDatabase
            } catch (rethrow: CancellationException) {
                throw rethrow
            } catch (ex: Exception) {
            }
        }
    }

    fun getStartPosition(episodeId: String) {
        _state.value = EpisodeState.FirstLoading
        viewModelScope.launch {
            try {
                val time = getEpisodeTimeUseCase(context, episodeId)
                getCollections()
                while (collections.isEmpty()) {}
                _state.value = EpisodeState.Initial(time.timeInSeconds, collections)
            } catch (rethrow: CancellationException) {
                throw rethrow
            } catch (ex: Exception) {
                _state.value = EpisodeState.Failure(ex.message.toString())
            }
        }
    }

    fun addMovieInCollection(index: Int, movieId: String) {
        val collectionInfo = collections[index]

        viewModelScope.launch {
            try{
                val movieValue = MovieValueDto(movieId)
                postMovieInCollectionUseCase(context, collectionInfo.id, movieValue)
            } catch (rethrow: CancellationException) {
                throw rethrow
            } catch (ex: Exception) {
                _state.value = EpisodeState.Failure(ex.message.toString())
            }
        }
    }


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

    fun postEpisodeTime(episodeId: String, time: Int) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val episodeTime = EpisodeTimeDto(time)
                postEpisodeTimeUseCase(context, episodeId, episodeTime)
            } catch (rethrow: CancellationException) {
                throw rethrow
            } catch (ex: Exception) {
            }
        }
    }
}