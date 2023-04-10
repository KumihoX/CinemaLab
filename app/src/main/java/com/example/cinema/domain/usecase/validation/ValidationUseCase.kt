package com.example.cinema.domain.usecase.validation

import android.util.Patterns

class ValidationUseCase {

    fun checkOnEmptiness(data: String): Boolean {
        return data.isBlank()
    }

    fun checkEmailValidity(email: String): Boolean {
        return email.let { Patterns.EMAIL_ADDRESS.matcher(it).matches() }
    }

    fun checkSameness(firstPasswordData: String, secondPasswordData: String): Boolean {
        return firstPasswordData == secondPasswordData
    }
}

class SignInValidationForm {
    private val validation = ValidationUseCase()

    fun validateFields(
        email: String,
        password: String
    ): String {
        var message = ""

        if (validation.checkOnEmptiness(email)) {
            message += "Заполните поле \"E-mail\"\n"
        } else if (!validation.checkEmailValidity(email)) {
            message += "Введенный email не соответствует шаблону: example@mail.ru\n"
        }
        if (validation.checkOnEmptiness(password)) message += "Заполните поле \"Пароль\"\n"

        return message
    }
}

class SignUpValidationForm {
    private val validation = ValidationUseCase()

    fun validateFields(
        name: String,
        surname: String,
        email: String,
        password: String,
        duplicatePassword: String
    ): String {
        var message = ""

        if (validation.checkOnEmptiness(name)) message += "Заполните поле \"Имя\"\n"
        if (validation.checkOnEmptiness(surname)) message += "Заполните поле \"Фамилия\"\n"

        if (validation.checkOnEmptiness(email)) {
            message += "Заполните поле \"E-mail\"\n"
        } else if (!validation.checkEmailValidity(email)) {
            message += "Введенный email не соответствует шаблону: example@mail.ru\n"
        }

        if (!(validation.checkOnEmptiness(password) && validation.checkOnEmptiness(duplicatePassword))) {
            if (!validation.checkSameness(
                    password,
                    duplicatePassword
                )
            ) message += "Введенные пароли не совпадают\n"
        } else {
            if (validation.checkOnEmptiness(password)) message += "Заполните поле \"Пароль\"\n"
            if (validation.checkOnEmptiness(duplicatePassword)) message += "Заполните поле \"Повторите пароль\"\n"
        }

        return message
    }
}