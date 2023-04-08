package com.example.cinema.presentation.collections

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinema.domain.usecase.collection.DeleteCollectionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditCollectionViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val deleteCollectionUseCase: DeleteCollectionUseCase
) : ViewModel() {

    sealed class EditCollectionState {
        object Initial : EditCollectionState()
        object Loading : EditCollectionState()
        object Success : EditCollectionState()
        class Failure(val errorMessage: String) : EditCollectionState()
    }

    private val _state = MutableLiveData<EditCollectionState>(EditCollectionState.Initial)
    val state: LiveData<EditCollectionState> = _state

    fun deleteCollection(collectionId: String) {
        _state.value = EditCollectionState.Loading
        viewModelScope.launch {
            try {
                deleteCollectionUseCase(context, collectionId)
                _state.value = EditCollectionState.Success
            } catch (rethrow: CancellationException) {
                throw rethrow
            } catch (ex: Exception) {
                _state.value = EditCollectionState.Failure(ex.message.toString())
            }
        }
    }
}