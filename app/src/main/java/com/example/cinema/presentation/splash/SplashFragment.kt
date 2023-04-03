package com.example.cinema.presentation.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.cinema.R
import com.example.cinema.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : Fragment() {

    private lateinit var binding: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mainView = inflater.inflate(R.layout.fragment_splash, container, false)
        binding = FragmentSplashBinding.bind(mainView)

        Handler(Looper.getMainLooper()).postDelayed({
            navigateToSignUpFragment()
        }, 3000)
        return binding.root
    }

    private fun navigateToSignUpFragment() {
        findNavController().navigate(R.id.action_splashFragment_to_signUpFragment)
    }
}