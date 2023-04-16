package com.example.cinema.presentation.bottomnavigation.collections.change.create

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinema.R
import com.example.cinema.data.remote.api.dto.CollectionFormDto
import com.example.cinema.data.remote.database.entity.CollectionEntity
import com.example.cinema.domain.usecase.collection.AddCollectionInDatabaseUseCase
import com.example.cinema.domain.usecase.collection.PostCollectionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateCollectionViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val postCollectionUseCase: PostCollectionUseCase,
    private val addCollectionInDatabaseUseCase: AddCollectionInDatabaseUseCase
) : ViewModel() {
    sealed class CreateCollectionState {
        object Initial : CreateCollectionState()
        object Loading : CreateCollectionState()
        object Success : CreateCollectionState()
        class Failure(val errorMessage: String) : CreateCollectionState()
    }

    private val _state = MutableLiveData<CreateCollectionState>(CreateCollectionState.Initial)
    val state: LiveData<CreateCollectionState> = _state

    fun postCollection(name: String, icon: Int) {
        var iconImage = R.drawable.collection_icon_01
        if (icon != 0) {
            iconImage = icon
        }

        if (name == "Избранное" || name.isEmpty()) {
            _state.value = CreateCollectionState.Failure("Нельзя создать коллекцию с таким именем")
            return
        }

        _state.value = CreateCollectionState.Loading
        viewModelScope.launch {
            try {
                val collectionForm = CollectionFormDto(name)
                val collectionData = postCollectionUseCase(context, collectionForm)

                val collectionEntity = CollectionEntity(
                    collectionData.name,
                    iconImage,
                    collectionData.collectionId
                )
                addCollection(collectionEntity)
                _state.value = CreateCollectionState.Success
            } catch (rethrow: CancellationException) {
                throw rethrow
            } catch (ex: Exception) {
                _state.value = CreateCollectionState.Failure(ex.message.toString())
            }
        }
    }

    private fun addCollection(collectionEntity: CollectionEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                addCollectionInDatabaseUseCase(collectionEntity)
            } catch (rethrow: CancellationException) {
                throw rethrow
            } catch (ex: Exception) {
            }
        }
    }
}