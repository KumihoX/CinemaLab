package com.example.cinema.domain.usecase.validation

import android.content.Context
import android.util.Patterns
import com.example.cinema.R

class ValidationUseCase {

    fun checkOnEmptiness(data: String): Boolean {
        return data.isBlank()
    }

    fun checkEmailValidity(email: String): Boolean {
        val parseEmail = email.split("@")
        if (parseEmail.size == 2) {
            val name = email.split("@")[0]
            val domainName = email.split("@")[1]
            val nameValid = name.matches(Regex("^[^A-Z~!@#\$%^&*+-]+$"))
            val domainNameValid = domainName.matches(Regex("^[^A-Z~!@#\$%^&*+-]+$"))

            return email.let {
                Patterns.EMAIL_ADDRESS.matcher(it).matches()
            } && nameValid && domainNameValid
        }
        return false
    }

    fun checkSameness(firstPasswordData: String, secondPasswordData: String): Boolean {
        return firstPasswordData == secondPasswordData
    }
}

class SignInValidationForm {
    private val validation = ValidationUseCase()

    fun validateFields(
        context: Context,
        email: String,
        password: String
    ): String {
        var message = ""

        if (validation.checkOnEmptiness(email)) {
            message += context.getString(R.string.error_empty_email)
        } else if (!validation.checkEmailValidity(email)) {
            message += context.getString(R.string.error_invalid_email)
        }
        if (validation.checkOnEmptiness(password)) message += context.getString(R.string.error_empty_password)

        return message
    }
}

class SignUpValidationForm {
    private val validation = ValidationUseCase()

    fun validateFields(
        context: Context,
        name: String,
        surname: String,
        email: String,
        password: String,
        duplicatePassword: String
    ): String {
        var message = ""

        if (validation.checkOnEmptiness(name)) message += context.getString(R.string.error_empty_name)
        if (validation.checkOnEmptiness(surname)) message += context.getString(R.string.error_empty_surname)

        if (validation.checkOnEmptiness(email)) {
            message += context.getString(R.string.error_empty_email)
        } else if (!validation.checkEmailValidity(email)) {
            message += context.getString(R.string.error_invalid_email)
        }

        if (!(validation.checkOnEmptiness(password) && validation.checkOnEmptiness(duplicatePassword))) {
            if (!validation.checkSameness(
                    password,
                    duplicatePassword
                )
            ) message += context.getString(R.string.error_passwords_is_not_sameness)
        } else {
            if (validation.checkOnEmptiness(password)) message += context.getString(R.string.error_empty_password)
            if (validation.checkOnEmptiness(duplicatePassword)) message += context.getString(R.string.error_empty_duplicate_password)
        }

        return message
    }
}