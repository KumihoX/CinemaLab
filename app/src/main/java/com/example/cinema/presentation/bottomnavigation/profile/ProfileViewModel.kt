package com.example.cinema.presentation.bottomnavigation.profile

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinema.data.remote.api.dto.UserDto
import com.example.cinema.domain.usecase.collection.DeleteAllCollectionsUseCase
import com.example.cinema.domain.usecase.profile.GetProfileUseCase
import com.example.cinema.domain.usecase.profile.PostAvatarUseCase
import com.example.cinema.domain.usecase.storage.DeleteFavoriteCollectionUseCase
import com.example.cinema.domain.usecase.storage.DeleteTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val getProfileUseCase: GetProfileUseCase,
    private val postAvatarUseCase: PostAvatarUseCase,
    private val deleteAllCollectionsUseCase: DeleteAllCollectionsUseCase
) : ViewModel() {

    sealed class ProfileState {
        object Loading : ProfileState()
        class Success(
            val user: UserDto
        ) : ProfileState()

        class Failure(val errorMessage: String) : ProfileState()
    }

    private val _state = MutableLiveData<ProfileState>(ProfileState.Loading)
    val state: LiveData<ProfileState> = _state

    fun getProfileData() {
        _state.value = ProfileState.Loading
        viewModelScope.launch {
            try {
                val userData = getProfileUseCase(context)
                _state.value = ProfileState.Success(userData)
            } catch (rethrow: CancellationException) {
                throw rethrow
            } catch (ex: Exception) {
                _state.value = ProfileState.Failure(ex.message.toString())
            }
        }
    }

    fun loadProfileImage(image: File) {
        _state.value = ProfileState.Loading

        viewModelScope.launch {
            try {
                postAvatarUseCase(context, image)
                val data = getProfileUseCase(context)
                _state.value = ProfileState.Success(data)
            } catch (ex: Exception) {
                _state.value = ProfileState.Failure(ex.message.toString())
            }
        }
    }

    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val deleteTokenUseCase = DeleteTokenUseCase(context)
                deleteTokenUseCase.execute()

                val deleteFavoriteCollectionUseCase = DeleteFavoriteCollectionUseCase(context)
                deleteFavoriteCollectionUseCase.execute()

                deleteAllCollectionsUseCase()
            } catch (rethrow: CancellationException) {
                throw rethrow
            } catch (ex: Exception) {
                _state.value = ProfileState.Failure(ex.message.toString())
            }
        }
    }
}