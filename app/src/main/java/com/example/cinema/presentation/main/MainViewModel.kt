package com.example.cinema.presentation.main

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinema.domain.usecase.main.GetCoverUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val getCoverUseCase: GetCoverUseCase
): ViewModel() {
    private val _cover = MutableLiveData("")
    val cover: LiveData<String> = _cover

    init {
        getCover()
    }

    private fun getCover() {
        viewModelScope.launch {
            try {
                val coverImage = getCoverUseCase(context)
                _cover.value = coverImage.backgroundImage
            } catch (rethrow: CancellationException) {
                throw rethrow
            } catch (ex: Exception) {

            }
        }
    }
}