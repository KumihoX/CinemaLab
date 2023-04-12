package com.example.cinema.presentation.bottomnavigation.collections.detail

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinema.data.remote.api.dto.CollectionListItemDto
import com.example.cinema.data.remote.api.dto.MovieDto
import com.example.cinema.data.remote.database.entity.CollectionEntity
import com.example.cinema.domain.usecase.collection.GetCollectionInfoUseCase
import com.example.cinema.domain.usecase.storage.GetFavoriteCollectionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionInfoViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val getCollectionInfoUseCase: GetCollectionInfoUseCase
) : ViewModel() {

    sealed class CollectionInfoState {
        object Loading : CollectionInfoState()
        class Failure(val errorMessage: String) : CollectionInfoState()
        class Success(
            val collectionInfo: CollectionEntity,
            val moviesInCollection: List<MovieDto>,
            val isFavorite: Boolean
        ) : CollectionInfoState()
    }

    private val favoriteCollectionId = GetFavoriteCollectionUseCase(context).execute().collectionId

    private val _state = MutableLiveData<CollectionInfoState>(CollectionInfoState.Loading)
    val state: LiveData<CollectionInfoState> = _state

    fun getInfo(info: CollectionEntity) {
        getCollectionInfo(info)
    }

    private fun getCollectionInfo(info: CollectionEntity) {
        _state.value = CollectionInfoState.Loading
        viewModelScope.launch {
            try {
                val moviesInCollection = getCollectionInfoUseCase(context, info.id)
                val isFavorite = (info.id == favoriteCollectionId)

                _state.value = CollectionInfoState.Success(info, moviesInCollection, isFavorite)
            } catch (rethrow: CancellationException) {
                throw rethrow
            } catch (ex: Exception) {
                _state.value = CollectionInfoState.Failure(ex.message.toString())
            }
        }
    }
}