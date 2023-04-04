package com.example.cinema.presentation.collections

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinema.data.remote.dto.CollectionFormDto
import com.example.cinema.data.remote.dto.MovieDto
import com.example.cinema.domain.usecase.collection.PostCollectionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateCollectionViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val postCollectionUseCase: PostCollectionUseCase
) : ViewModel() {

    private val _requestEnd: MutableLiveData<Boolean> = MutableLiveData(false)
    val requestEnd: LiveData<Boolean> = _requestEnd
    fun postCollection(name:String) {
        viewModelScope.launch {
            try {
                val collectionForm = CollectionFormDto(name)
                postCollectionUseCase(context, collectionForm)
                _requestEnd.value = true
            } catch (rethrow: CancellationException) {
                throw rethrow
            } catch (ex: Exception) {

            }
        }
    }
}