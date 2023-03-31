package com.example.cinema.presentation.profile

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinema.domain.usecase.profile.GetProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val getProfileUseCase: GetProfileUseCase
) : ViewModel() {
    private val _userName = MutableLiveData("")
    val userName: LiveData<String> = _userName

    private val _userEmail = MutableLiveData("")
    val userEmail: LiveData<String> = _userEmail

    private val _avatarUrl = MutableLiveData("")
    val avatarUrl: LiveData<String> = _avatarUrl

    init {
        getProfileData()
    }
    private fun getProfileData() {
        viewModelScope.launch {
            try {
                val userData = getProfileUseCase(context)
                _userName.value = userData.firstName + userData.lastName
                _userEmail.value = userData.email
                _avatarUrl.value = userData.avatar
            } catch (rethrow: CancellationException) {
                throw rethrow
            } catch (ex: Exception) {

            }
        }

    }
}