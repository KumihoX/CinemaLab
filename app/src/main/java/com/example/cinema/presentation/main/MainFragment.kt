package com.example.cinema.presentation.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.cinema.R
import com.example.cinema.databinding.FragmentMainBinding
import com.example.cinema.databinding.FragmentSignInBinding
import com.example.cinema.databinding.FragmentSignUpBinding
import com.example.cinema.presentation.signup.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mainView = inflater.inflate(R.layout.fragment_main, container, false)
        binding = FragmentMainBinding.bind(mainView)

        return binding.root
    }
}