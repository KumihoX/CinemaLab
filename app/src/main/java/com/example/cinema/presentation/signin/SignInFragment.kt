package com.example.cinema.presentation.signin

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
import com.example.cinema.databinding.FragmentSignInBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : Fragment() {
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

    override fun onStart() {
        addClickListenersOnButtons()
        val stateObserver = Observer<SignInViewModel.SignInState> {
            when (it) {
                SignInViewModel.SignInState.Loading -> {
                    binding.signInProgressBar.visibility = View.VISIBLE
                    binding.comeInButton.isEnabled = false
                    binding.registrationButton.isEnabled = false
                }
                SignInViewModel.SignInState.Initial -> {
                    binding.signInProgressBar.visibility = View.GONE
                    binding.comeInButton.isEnabled = true
                    binding.registrationButton.isEnabled = true
                }
                is SignInViewModel.SignInState.Success -> {
                    binding.signInProgressBar.visibility = View.GONE
                    binding.comeInButton.isEnabled = true
                    binding.registrationButton.isEnabled = true
                    navigateToMainFragment()
                }
                is SignInViewModel.SignInState.Failure -> {
                    binding.signInProgressBar.visibility = View.GONE
                    binding.comeInButton.isEnabled = true
                    binding.registrationButton.isEnabled = true
                    createErrorDialog(it.errorMessage)
                }
            }
        }
        viewModel.state.observe(viewLifecycleOwner, stateObserver)

        super.onStart()
    }

    private fun addClickListenersOnButtons() {
        comeInButtonClick()
        registrationButtonClick()
    }

    private fun comeInButtonClick() {
        binding.comeInButton.setOnClickListener {
            validateFields()
        }
    }

    private fun registrationButtonClick() {
        binding.registrationButton.setOnClickListener {
            navigateToSignUpFragment()
        }
    }

    private fun navigateToSignUpFragment() {
        findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
    }

    private fun navigateToMainFragment() {
        findNavController().navigate(R.id.action_signInFragment_to_bottomNavigationActivity)
    }

    private fun createErrorDialog(message: String) {
        val builder = AlertDialog.Builder(context)

        builder.setTitle(R.string.incorrect_input_data)
        builder.setMessage(message)
        builder.show()
    }

    private fun validateFields() {
        viewModel.validateEditTexts(
            binding.emailEditText.text.toString(),
            binding.passwordEditText.text.toString()
        )
    }
}