package com.example.cinema.presentation.collections

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinema.data.remote.dto.CollectionListItemDto
import com.example.cinema.domain.usecase.collection.GetCollectionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionsViewModel  @Inject constructor(
    @ApplicationContext private val context: Context,
    private val getCollectionsUseCase: GetCollectionsUseCase
) : ViewModel() {
    private val _collectionsList: MutableLiveData<List<CollectionListItemDto>> = MutableLiveData(emptyList())
    val collectionsList: LiveData<List<CollectionListItemDto>> = _collectionsList

    init {
        _collectionsList.value = emptyList()
        getCollections()
    }

    private fun getCollections() {
        viewModelScope.launch {
            try{
                _collectionsList.value = getCollectionsUseCase(context)
            } catch (rethrow: CancellationException) {
                throw rethrow
            } catch (ex: Exception) {

            }
        }
    }
}