package com.example.cinema.presentation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.cinema.R
import com.example.cinema.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mainView = inflater.inflate(R.layout.fragment_profile, container, false)
        binding = FragmentProfileBinding.bind(mainView)

        addAvatar()
        addName()
        addEmail()

        return binding.root
    }

    private fun addAvatar() {
        val avatarUrlObserver = Observer<String> { newState ->
            if (newState.isNotEmpty()) {
                val imageView = binding.userAvatar
                Glide.with(this).load(newState).into(imageView)
            }
        }
        viewModel.avatarUrl.observe(viewLifecycleOwner, avatarUrlObserver)
    }

    private fun addName() {
        val nameObserver = Observer<String> { newState ->
            if (newState.isNotEmpty()) {
                binding.userName.text = newState
            }
        }
        viewModel.userName.observe(viewLifecycleOwner, nameObserver)
    }

    private fun addEmail() {
        val emailObserver = Observer<String> { newState ->
            if (newState.isNotEmpty()) {
                binding.userEmail.text = newState
            }
        }
        viewModel.userEmail.observe(viewLifecycleOwner, emailObserver)
    }
}