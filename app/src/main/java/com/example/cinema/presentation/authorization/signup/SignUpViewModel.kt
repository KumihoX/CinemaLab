package com.example.cinema.presentation.authorization.signup

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinema.R
import com.example.cinema.data.remote.api.dto.AuthTokenPairDto
import com.example.cinema.data.remote.api.dto.CollectionFormDto
import com.example.cinema.data.remote.api.dto.RegistrationBodyDto
import com.example.cinema.data.remote.database.entity.CollectionEntity
import com.example.cinema.domain.usecase.collection.AddCollectionInDatabaseUseCase
import com.example.cinema.domain.usecase.collection.PostCollectionUseCase
import com.example.cinema.domain.usecase.signup.RegisterUseCase
import com.example.cinema.domain.usecase.storage.SaveFavoriteCollectionUseCase
import com.example.cinema.domain.usecase.storage.SaveTokenUseCase
import com.example.cinema.domain.usecase.validation.SignUpValidationForm
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val registerUseCase: RegisterUseCase,
    private val postCollectionUseCase: PostCollectionUseCase,
    private val addCollectionInDatabaseUseCase: AddCollectionInDatabaseUseCase
) : ViewModel() {
    sealed class SignUpState {
        object Initial : SignUpState()
        object Loading : SignUpState()
        class Failure(val errorMessage: String) : SignUpState()
        class Success(val tokenPair: AuthTokenPairDto) :
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
        val message = validateClass.validateFields(context, name, surname, email, password, repeatPassword)

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

                addCollectionInDatabase()

                _state.value = SignUpState.Success(token)
            } catch (rethrow: CancellationException) {
                throw rethrow
            } catch (ex: Exception) {
                _state.value = SignUpState.Failure(ex.message.toString())
            }
        }
    }

    private fun addCollectionInDatabase() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val collectionFrom = CollectionFormDto(context.getString(R.string.favorites))
                val collection = postCollectionUseCase(context, collectionFrom)

                val saveFavoriteCollectionUseCase = SaveFavoriteCollectionUseCase(context)
                saveFavoriteCollectionUseCase.execute(collection)

                val collectionEntity = CollectionEntity(
                    collection.name,
                    R.drawable.collection_icon_01,
                    collection.collectionId
                )
                addCollectionInDatabaseUseCase(collectionEntity)

            } catch (rethrow: CancellationException) {
                throw rethrow
            } catch (ex: Exception) {
            }
        }
    }
}