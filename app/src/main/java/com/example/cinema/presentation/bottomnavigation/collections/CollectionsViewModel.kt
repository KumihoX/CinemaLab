package com.example.cinema.presentation.bottomnavigation.collections

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinema.data.remote.database.entity.CollectionEntity
import com.example.cinema.domain.usecase.collection.GetCollectionsFromDatabaseUseCase
import com.example.cinema.domain.usecase.storage.GetFavoriteCollectionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionsViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val getCollectionsFromDatabaseUseCase: GetCollectionsFromDatabaseUseCase
) : ViewModel() {

    sealed class CollectionsState {
        object Loading : CollectionsState()
        class Success(
            val collections: List<CollectionEntity>
        ) : CollectionsState()

        class Failure(val errorMessage: String) : CollectionsState()
    }

    private val favoriteCollectionId = GetFavoriteCollectionUseCase(context).execute().collectionId

    private val _state = MutableLiveData<CollectionsState>(CollectionsState.Loading)
    val state: LiveData<CollectionsState> = _state

    fun getCollections() {
        _state.value = CollectionsState.Loading
        var getCollections: List<CollectionEntity> = emptyList()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getCollections = getCollectionsFromDatabaseUseCase()

            } catch (rethrow: CancellationException) {
                throw rethrow
            } catch (ex: Exception) {
            }
        }
        while (getCollections.isEmpty()) {
        }
        var favoriteCollectionIndex = 0
        for (i in getCollections.indices) {
            if (getCollections[i].id == favoriteCollectionId) {
                favoriteCollectionIndex = i
            }
        }

        val collections: MutableList<CollectionEntity> = mutableListOf()
        collections.add(getCollections[favoriteCollectionIndex])

        val leftPart = getCollections.slice(0 until favoriteCollectionIndex)
        val rightPart =
            getCollections.slice(favoriteCollectionIndex + 1 until getCollections.size)

        collections.addAll(leftPart)
        collections.addAll(rightPart)

        _state.value = CollectionsState.Success(collections)
    }
}