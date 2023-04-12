package com.example.cinema.presentation.authorization.signin

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinema.R
import com.example.cinema.data.remote.api.dto.AuthCredentialDto
import com.example.cinema.data.remote.api.dto.AuthTokenPairDto
import com.example.cinema.data.remote.database.entity.CollectionEntity
import com.example.cinema.domain.usecase.collection.AddCollectionInDatabaseUseCase
import com.example.cinema.domain.usecase.collection.GetCollectionsUseCase
import com.example.cinema.domain.usecase.signin.ComeInUseCase
import com.example.cinema.domain.usecase.storage.SaveFavoriteCollectionUseCase
import com.example.cinema.domain.usecase.storage.SaveTokenUseCase
import com.example.cinema.domain.usecase.validation.SignInValidationForm
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val comeInUseCase: ComeInUseCase,
    private val getCollectionsUseCase: GetCollectionsUseCase,
    private val addCollectionInDatabaseUseCase: AddCollectionInDatabaseUseCase
) : ViewModel() {

    sealed class SignInState {
        object Initial : SignInState()
        object Loading : SignInState()
        class Failure(val errorMessage: String) : SignInState()
        class Success(val tokenPair: AuthTokenPairDto) : SignInState()
    }

    private val validateClass = SignInValidationForm()

    private val _state = MutableLiveData<SignInState>(SignInState.Initial)
    val state: LiveData<SignInState> = _state

    fun validateEditTexts(
        email: String,
        password: String
    ) {
        val message = validateClass.validateFields(email, password)

        if (message != "") {
            _state.value = SignInState.Failure(message)
        } else {
            comeIn(email, password)
        }
    }

    private fun comeIn(
        email: String,
        password: String
    ) {
        val userData = AuthCredentialDto(
            email = email,
            password = password
        )

        viewModelScope.launch {
            try {
                _state.value = SignInState.Loading

                val token = comeInUseCase(userData)
                val saveTokenUseCase = SaveTokenUseCase(context)
                saveTokenUseCase.execute(token)
                getCollections()

                _state.value = SignInState.Success(token)
            } catch (rethrow: CancellationException) {
                throw rethrow
            } catch (ex: Exception) {
                _state.value = SignInState.Failure(ex.message.toString())
            }
        }
    }

    private fun getCollections() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val collections = getCollectionsUseCase(context)
                for (i in collections.indices) {

                    val collectionEntity = CollectionEntity(
                        collections[i].name,
                        R.drawable.collection_icon_01,
                        collections[i].collectionId
                    )
                    addCollectionInDatabaseUseCase(collectionEntity)

                    if (collections[i].name == context.getString(R.string.favorites)) {
                        val saveFavoriteCollectionUseCase = SaveFavoriteCollectionUseCase(context)
                        saveFavoriteCollectionUseCase.execute(collections[i])
                        break
                    }
                }
            } catch (rethrow: CancellationException) {
                throw rethrow
            } catch (ex: Exception) {
            }
        }
    }

}