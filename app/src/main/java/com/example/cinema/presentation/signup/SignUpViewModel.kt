package com.example.cinema.presentation.signup

import android.content.Context
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.cinema.R
import com.example.cinema.data.remote.dto.RegistrationBodyDto
import com.example.cinema.domain.usecase.signup.RegisterUseCase
import com.example.cinema.domain.usecase.token.SaveTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    private val _allFieldsValid = MutableLiveData(true)
    val allFieldsValid: LiveData<Boolean> = _allFieldsValid

    private val _serverErrors = MutableLiveData(false)
    val serverErrors: LiveData<Boolean> = _serverErrors

    private val _message = MutableLiveData("")
    val message: LiveData<String> = _message

    fun validateEditTexts(
        name: String,
        surname: String,
        email: String,
        password: String,
        repeatPassword: String,
        navController: NavController
    ) {
        _message.value = ""
        _allFieldsValid.value = true
        nameValidityCheck(name)
        surnameValidityCheck(surname)
        emailValidityCheck(email)
        passwordValidityCheck(password)
        repeatPasswordValidityCheck(password, repeatPassword)

        if (allFieldsValid.value == true) {
            register(name, surname, email, password, navController)
        }
    }

    private fun register(
        name: String,
        surname: String,
        email: String,
        password: String,
        navController: NavController
    ) {
        val userData = RegistrationBodyDto(
            email = email,
            firstName = name,
            lastName = surname,
            password = password
        )

        viewModelScope.launch {
            try {
                val token = registerUseCase(userData)
                val saveTokenUseCase = SaveTokenUseCase(context)
                saveTokenUseCase.execute(token)
                navController.navigate(R.id.action_signUpFragment_to_bottomNavigationActivity)
            } catch (rethrow: CancellationException) {
                throw rethrow
            } catch (ex: Exception) {

            }
        }
    }

    private fun nameValidityCheck(name: String) {
        if (name.isEmpty()) {
            _allFieldsValid.value = false
            _message.value += "Заполните поле \"Имя\"\n"
            return
        }
    }

    private fun surnameValidityCheck(surname: String) {
        if (surname.isEmpty()) {
            _allFieldsValid.value = false
            _message.value += "Заполните поле \"Фамилия\"\n"
            return
        }
    }

    //TODO: Переписать паттерн проверки email
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

    private fun repeatPasswordValidityCheck(password: String, repeatPassword: String) {
        if (repeatPassword.isEmpty()) {
            _allFieldsValid.value = false
            _message.value += "Заполните поле \"Повторите пароль\"\n"
            return
        }
        if (password != repeatPassword) {
            _allFieldsValid.value = false
            _message.value += "Введенные пароли не совпадают\n"
            return
        }
    }

}