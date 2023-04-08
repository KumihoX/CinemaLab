package com.example.cinema.presentation.signup

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinema.R
import com.example.cinema.data.remote.dto.AuthTokenPairDto
import com.example.cinema.data.remote.dto.CollectionFormDto
import com.example.cinema.data.remote.dto.CollectionListItemDto
import com.example.cinema.data.remote.dto.RegistrationBodyDto
import com.example.cinema.domain.usecase.collection.PostCollectionUseCase
import com.example.cinema.domain.usecase.signup.RegisterUseCase
import com.example.cinema.domain.usecase.storage.SaveFavoriteCollectionUseCase
import com.example.cinema.domain.usecase.storage.SaveTokenUseCase
import com.example.cinema.domain.usecase.validation.SignUpValidationForm
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val registerUseCase: RegisterUseCase,
    private val postCollectionUseCase: PostCollectionUseCase
) : ViewModel() {
    sealed class SignUpState {
        object Initial : SignUpState()
        object Loading : SignUpState()
        class Failure(val errorMessage: String) : SignUpState()
        class Success(val tokenPair: AuthTokenPairDto, val collection: CollectionListItemDto) :
            SignUpState()
    }

    private val validateClass = SignUpValidationForm()

    private val _state = MutableLiveData<SignUpState>(SignUpState.Initial)
    val state: LiveData<SignUpState> = _state

    fun validateEditTexts(
        name: String,
        surname: String,
        email: String,
        password: String,
        repeatPassword: String
    ) {
        val message = validateClass.validateFields(name, surname, email, password, repeatPassword)

        if (message != "") {
            _state.value = SignUpState.Failure(message)
        } else {
            register(name, surname, email, password)
        }
    }

    private fun register(
        name: String,
        surname: String,
        email: String,
        password: String
    ) {
        val userData = RegistrationBodyDto(
            email = email,
            firstName = name,
            lastName = surname,
            password = password
        )

        viewModelScope.launch {
            try {
                _state.value = SignUpState.Loading
                val token = registerUseCase(userData)

                val saveTokenUseCase = SaveTokenUseCase(context)
                saveTokenUseCase.execute(token)

                val collectionFrom = CollectionFormDto(context.getString(R.string.favorites))
                val collection = postCollectionUseCase(context, collectionFrom)

                val saveFavoriteCollectionUseCase = SaveFavoriteCollectionUseCase(context)
                saveFavoriteCollectionUseCase.execute(collection)

                _state.value = SignUpState.Success(token, collection)
            } catch (rethrow: CancellationException) {
                throw rethrow
            } catch (ex: Exception) {
                _state.value = SignUpState.Failure(ex.message.toString())
            }
        }
    }
}