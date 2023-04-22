package com.example.cinema.presentation.authorization.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.cinema.R
import com.example.cinema.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : Fragment() {

    private lateinit var binding: FragmentSplashBinding
    private val viewModel: SplashViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mainView = inflater.inflate(R.layout.fragment_splash, container, false)
        binding = FragmentSplashBinding.bind(mainView)

        val stateObserver = Observer<SplashViewModel.SplashState> {
            when (it) {
                SplashViewModel.SplashState.Loading -> {}
                SplashViewModel.SplashState.FirstTime -> {
                    navigateToSignUpFragment()
                }
                SplashViewModel.SplashState.NotFirstTime -> {
                    navigateToSignInFragment()
                }
            }
        }
        viewModel.state.observe(viewLifecycleOwner, stateObserver)

        viewModel.getUserFirstTimeStatus()
        return binding.root
    }

    private fun navigateToSignUpFragment() {
        findNavController().navigate(R.id.action_splashFragment_to_signUpFragment)
    }

    private fun navigateToSignInFragment() {
        findNavController().navigate(R.id.action_splashFragment_to_signInFragment)
    }
}