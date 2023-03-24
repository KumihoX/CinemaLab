package com.example.cinema.presentation.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignUpViewModel: ViewModel() {

    private val _allFieldsValid = MutableLiveData(true)
    val allFieldsValid: LiveData<Boolean> = _allFieldsValid

    private val _message = MutableLiveData("")
    val message: LiveData<String> = _message

    fun validateEditTexts(name: String) {
        emptinessCheck(name)
    }

    private fun emptinessCheck(fieldContent: String) {
        if (fieldContent.isEmpty()) {
            _allFieldsValid.value = false
        }
    }

}