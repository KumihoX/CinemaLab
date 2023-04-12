package com.example.cinema.presentation.bottomnavigation.collections.change.edit

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinema.domain.usecase.collection.DeleteCollectionFromDatabaseUseCase
import com.example.cinema.domain.usecase.collection.DeleteCollectionUseCase
import com.example.cinema.domain.usecase.collection.EditCollectionInDatabaseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditCollectionViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val deleteCollectionUseCase: DeleteCollectionUseCase,
    private val editCollectionInDatabaseUseCase: EditCollectionInDatabaseUseCase,
    private val deleteCollectionFromDatabaseUseCase: DeleteCollectionFromDatabaseUseCase
) : ViewModel() {

    sealed class EditCollectionState {
        object Initial : EditCollectionState()
        object Loading : EditCollectionState()
        object Success : EditCollectionState()
        class Failure(val errorMessage: String) : EditCollectionState()
    }

    private val _state = MutableLiveData<EditCollectionState>(EditCollectionState.Initial)
    val state: LiveData<EditCollectionState> = _state

    fun saveChanges(collectionId: String, name: String, image: Int) {
        _state.value = EditCollectionState.Loading
        var scopeIsEnd = false
        viewModelScope.launch(Dispatchers.IO) {
            try {
                editCollectionInDatabaseUseCase(image,name,collectionId)
                scopeIsEnd = true
            } catch (rethrow: CancellationException) {
                throw rethrow
            } catch (ex: Exception) {
                _state.value = EditCollectionState.Failure(ex.message.toString())
            }
        }
        while(!scopeIsEnd){}
        _state.value = EditCollectionState.Success
    }

    fun deleteCollection(collectionId: String) {
        _state.value = EditCollectionState.Loading
        viewModelScope.launch {
            try {
                deleteCollectionUseCase(context, collectionId)
                deleteCollectionFromDatabase(collectionId)
                _state.value = EditCollectionState.Success
            } catch (rethrow: CancellationException) {
                throw rethrow
            } catch (ex: Exception) {
                _state.value = EditCollectionState.Failure(ex.message.toString())
            }
        }
    }

    private fun deleteCollectionFromDatabase(collectionId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                deleteCollectionFromDatabaseUseCase(collectionId)
            } catch (rethrow: CancellationException) {
                throw rethrow
            } catch (ex: Exception) {

            }
        }
    }
}