package com.example.cinema.presentation.signin

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.cinema.R
import com.example.cinema.databinding.FragmentSignInBinding
import com.example.cinema.presentation.signup.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment: Fragment() {
    private lateinit var binding: FragmentSignInBinding
    private val viewModel: SignInViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mainView = inflater.inflate(R.layout.fragment_sign_in, container, false)
        binding = FragmentSignInBinding.bind(mainView)

        addClickListenersOnButtons()

        return binding.root
    }

    private fun addClickListenersOnButtons() {
        comeInButtonClick()
        registrationButtonClick()
    }

    private fun comeInButtonClick() {
        binding.comeInButton.setOnClickListener{
            validateFields()
        }
    }

    private fun registrationButtonClick() {
        binding.registrationButton.setOnClickListener{
            navigateToSignUpFragment()
        }
    }

    private fun navigateToSignUpFragment() {
        findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
    }

    private fun navigateToMainFragment() {
        findNavController().navigate(R.id.action_signInFragment_to_bottomNavigationFragment)
    }

    private fun createErrorDialog() {
        val message = viewModel.message.value
        val builder = AlertDialog.Builder(context)

        builder.setTitle("Неправильно введенные данные")
        builder.setMessage(message)
        builder.show()
    }

    private fun validateFields() {
        val navController = findNavController()
        viewModel.validateEditTexts(
            binding.emailEditText.text.toString(),
            binding.passwordEditText.text.toString(),
            navController
        )

        if (viewModel.allFieldsValid.value == false) {
            createErrorDialog()
        }
    }
}