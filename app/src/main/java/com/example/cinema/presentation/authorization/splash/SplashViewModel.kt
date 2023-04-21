package com.example.cinema.presentation.authorization.splash

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cinema.domain.usecase.storage.ChangeUserFirstTimeStatusUseCase
import com.example.cinema.domain.usecase.storage.GetUserFirstTimeStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {
    sealed class SplashState {
        object Loading : SplashState()
        object FirstTime : SplashState()
        object NotFirstTime : SplashState()
    }

    private val _state = MutableLiveData<SplashState>(SplashState.Loading)
    val state: LiveData<SplashState> = _state

    fun getUserFirstTimeStatus() {
        val firstTimeStatus = GetUserFirstTimeStatusUseCase(context).execute()
        if (firstTimeStatus) {
            ChangeUserFirstTimeStatusUseCase(context).execute()
            _state.value = SplashState.FirstTime
            return
        }

        _state.value = SplashState.NotFirstTime
    }
}