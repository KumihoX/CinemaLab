package com.example.cinema.presentation.bottomnavigation.collections.change.selection

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cinema.domain.usecase.collection.GetCollectionInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class SelectionViewModel @Inject constructor() : ViewModel() {

    sealed class SelectionState {
        object Loading : SelectionState()
        object Success : SelectionState()
        class Failure(val errorMessage: String) : SelectionState()
    }

    private val _state = MutableLiveData<SelectionState>(SelectionState.Loading)
    val state: LiveData<SelectionState> = _state

    fun changeStateOnSuccess() {
        _state.value = SelectionState.Success
    }
}