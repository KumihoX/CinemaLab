package com.example.cinema.presentation.signin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.cinema.R
import com.example.cinema.databinding.FragmentSignInBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment: Fragment() {
    private lateinit var binding: FragmentSignInBinding

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
}