package com.example.cinema.presentation.bottomnavigation.collections.change.selection

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
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