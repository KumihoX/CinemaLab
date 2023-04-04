package com.example.cinema.presentation.collections

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinema.data.remote.dto.CollectionListItemDto
import com.example.cinema.data.remote.dto.MovieDto
import com.example.cinema.domain.usecase.collection.GetCollectionInfoUseCase
import com.example.cinema.domain.usecase.collection.GetCollectionsUseCase
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

    private val _collectionInfoList: MutableLiveData<List<MovieDto>> = MutableLiveData(emptyList())
    val collectionInfoList: LiveData<List<MovieDto>> = _collectionInfoList

    fun getInfo(info: CollectionListItemDto) {
        getCollectionInfo(info)
    }

    private fun getCollectionInfo(info: CollectionListItemDto) {
        viewModelScope.launch {
            try {
                _collectionInfoList.value = getCollectionInfoUseCase(context, info.collectionId)
            } catch (rethrow: CancellationException) {
                throw rethrow
            } catch (ex: Exception) {

            }
        }
    }
}