package com.example.cinema.presentation.signup

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.cinema.R
import com.example.cinema.databinding.FragmentSignUpBinding


class SignUpFragment: Fragment() {
    private lateinit var binding: FragmentSignUpBinding
    private val viewModel: SignUpViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mainView = inflater.inflate(R.layout.fragment_sign_up, container, false)
        binding = FragmentSignUpBinding.bind(mainView)

        addClickListenersOnButtons()

        return binding.root
    }

    private fun addClickListenersOnButtons() {
        registerButtonClick()
        iHaveAccButtonClick()
    }

    private fun registerButtonClick() {
        binding.registerButton.setOnClickListener{
            validateFields()
        }
    }

    private fun iHaveAccButtonClick() {
        binding.iHaveAccButton.setOnClickListener{
            navigateToSignInFragment()
        }
    }

    private fun navigateToSignInFragment() {

    }

    private fun createErrorDialog(message: String) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Неправильно введенные данные")
        builder.setMessage(message)
        builder.show()
    }

    private fun validateFields() {
        if (binding.nameEditText.text.toString().isEmpty()){
            createErrorDialog("Вы оставили поле \"Имя\" пустым")
        }
    }
}