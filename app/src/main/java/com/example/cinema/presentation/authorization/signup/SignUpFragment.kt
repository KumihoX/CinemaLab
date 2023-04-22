package com.example.cinema.presentation.authorization.signup

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.cinema.R
import com.example.cinema.databinding.FragmentSignUpBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding
    private val viewModel: SignUpViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mainView = inflater.inflate(R.layout.fragment_sign_up, container, false)
        binding = FragmentSignUpBinding.bind(mainView)

        return binding.root
    }

    override fun onStart() {
        addClickListenersOnButtons()
        val stateObserver = Observer<SignUpViewModel.SignUpState> {
            when (it) {
                SignUpViewModel.SignUpState.Loading -> {
                    binding.signUpProgressBar.visibility = View.VISIBLE
                    binding.registerButton.isEnabled = false
                    binding.iHaveAccButton.isEnabled = false
                }
                SignUpViewModel.SignUpState.Initial -> {
                    binding.signUpProgressBar.visibility = View.GONE
                    binding.registerButton.isEnabled = true
                    binding.iHaveAccButton.isEnabled = true
                }
                is SignUpViewModel.SignUpState.Success -> {
                    binding.signUpProgressBar.visibility = View.GONE
                    binding.registerButton.isEnabled = true
                    binding.iHaveAccButton.isEnabled = true
                    navigateToMainFragment()
                }
                is SignUpViewModel.SignUpState.Failure -> {
                    binding.signUpProgressBar.visibility = View.GONE
                    binding.registerButton.isEnabled = true
                    binding.iHaveAccButton.isEnabled = true
                    createErrorDialog(it.errorMessage)
                }
            }
        }
        viewModel.state.observe(viewLifecycleOwner, stateObserver)

        super.onStart()
    }

    private fun addClickListenersOnButtons() {
        registerButtonClick()
        iHaveAccButtonClick()
    }

    private fun registerButtonClick() {
        binding.registerButton.setOnClickListener {
            validateFields()
        }
    }

    private fun iHaveAccButtonClick() {
        binding.iHaveAccButton.setOnClickListener {
            navigateToSignInFragment()
        }
    }

    private fun navigateToSignInFragment() {
        findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
    }

    private fun navigateToMainFragment() {
        findNavController().navigate(R.id.action_signUpFragment_to_bottomNavigationActivity)
    }

    private fun createErrorDialog(message: String) {
        val builder = AlertDialog.Builder(context)

        builder.setTitle(getString(R.string.incorrect_input_data))
        builder.setMessage(message)
        builder.show()
    }

    private fun validateFields() {
        viewModel.validateEditTexts(
            binding.nameEditText.text.toString(),
            binding.surnameEditText.text.toString(),
            binding.emailEditText.text.toString(),
            binding.passwordEditText.text.toString(),
            binding.repeatPasswordEditText.text.toString()
        )
    }
}