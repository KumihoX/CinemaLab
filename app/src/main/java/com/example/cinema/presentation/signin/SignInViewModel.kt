package com.example.cinema.presentation.signin

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinema.data.remote.dto.AuthCredentialDto
import com.example.cinema.data.remote.dto.RegistrationBodyDto
import com.example.cinema.domain.usecase.signin.ComeInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val comeInUseCase: ComeInUseCase
): ViewModel() {
    private val _message = MutableLiveData("")
    val message: LiveData<String> = _message

    private val _allFieldsValid = MutableLiveData(true)
    val allFieldsValid: LiveData<Boolean> = _allFieldsValid


    fun validateEditTexts(
        email: String,
        password: String
    ) {
        _message.value = ""
        _allFieldsValid.value = true
        emailValidityCheck(email)
        passwordValidityCheck(password)

        if (allFieldsValid.value == true) {
            comeIn(email, password)
        }
    }

    private fun emailValidityCheck(email: String) {
        if (email.isEmpty()) {
            _allFieldsValid.value = false
            _message.value += "Заполните поле \"E-mail\"\n"
            return
        }
        if (!email.let { Patterns.EMAIL_ADDRESS.matcher(it).matches() }) {
            _allFieldsValid.value = false
            _message.value += "Введенный email не соответствует шаблону: example@mail.ru\n"
            return
        }
    }

    private fun passwordValidityCheck(password: String) {
        if (password.isEmpty()) {
            _allFieldsValid.value = false
            _message.value += "Заполните поле \"Пароль\"\n"
            return
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
            comeInUseCase(userData)
        }
    }
}