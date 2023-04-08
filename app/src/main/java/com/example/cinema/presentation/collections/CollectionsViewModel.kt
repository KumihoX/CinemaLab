package com.example.cinema.presentation.collections

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinema.data.remote.dto.CollectionListItemDto
import com.example.cinema.domain.usecase.collection.GetCollectionsUseCase
import com.example.cinema.domain.usecase.storage.GetFavoriteCollectionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionsViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val getCollectionsUseCase: GetCollectionsUseCase
) : ViewModel() {

    sealed class CollectionsState {
        object Loading : CollectionsState()
        class Success(
            val collections: List<CollectionListItemDto>
        ) : CollectionsState()

        class Failure(val errorMessage: String) : CollectionsState()
    }

    private val favoriteCollectionId = GetFavoriteCollectionUseCase(context).execute().collectionId

    private val _state = MutableLiveData<CollectionsState>(CollectionsState.Loading)
    val state: LiveData<CollectionsState> = _state

    fun getCollections() {
        _state.value = CollectionsState.Loading
        viewModelScope.launch {
            try {
                val getCollections = getCollectionsUseCase(context)

                var favoriteCollectionIndex = 0
                for (i in getCollections.indices) {
                    if (getCollections[i].collectionId == favoriteCollectionId) {
                        favoriteCollectionIndex = i
                    }
                }

                val collections: MutableList<CollectionListItemDto> = mutableListOf()
                collections.add(getCollections[favoriteCollectionIndex])

                val leftPart = getCollections.slice(0 until favoriteCollectionIndex)
                val rightPart =
                    getCollections.slice(favoriteCollectionIndex + 1 until getCollections.size)

                collections.addAll(leftPart)
                collections.addAll(rightPart)

                _state.value = CollectionsState.Success(collections)
            } catch (rethrow: CancellationException) {
                throw rethrow
            } catch (ex: Exception) {
                _state.value = CollectionsState.Failure(ex.message.toString())
            }
        }
    }
}