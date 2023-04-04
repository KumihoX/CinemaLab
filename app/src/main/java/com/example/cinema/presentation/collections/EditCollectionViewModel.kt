package com.example.cinema.presentation.collections

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinema.domain.usecase.collection.DeleteCollectionUseCase
import com.example.cinema.domain.usecase.collection.GetCollectionInfoUseCase
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
    fun deleteCollection(collectionId: String) {
        viewModelScope.launch {
            try {
                deleteCollectionUseCase(context, collectionId)
            } catch (rethrow: CancellationException) {
                throw rethrow
            } catch (ex: Exception) {

            }
        }
    }
}