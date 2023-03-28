package com.example.cinema.presentation.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideModule
import com.example.cinema.R
import com.example.cinema.databinding.FragmentMainBinding
import com.example.cinema.databinding.FragmentSignInBinding
import com.example.cinema.databinding.FragmentSignUpBinding
import com.example.cinema.presentation.signin.SignInViewModel
import com.example.cinema.presentation.signup.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@GlideModule
class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mainView = inflater.inflate(R.layout.fragment_main, container, false)
        binding = FragmentMainBinding.bind(mainView)

        val coverObserver = Observer<String> { newState ->
            if (newState.isNotEmpty()) {
                addCover(newState)
            }
        }
        viewModel.cover.observe(viewLifecycleOwner, coverObserver)



        return binding.root
    }

    private fun addCover(imageUrl: String) {
        val imageView = binding.cover
        imageView.visibility = View.VISIBLE

        Glide.with(this).load(imageUrl).into(imageView)
    }
}