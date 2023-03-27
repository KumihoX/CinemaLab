package com.example.cinema.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.cinema.R
import com.example.cinema.databinding.BottomNavBarBinding

class BottomNavigationFragment: Fragment() {

    private lateinit var binding: BottomNavBarBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mainView = inflater.inflate(R.layout.bottom_nav_bar, container, false)
        binding = BottomNavBarBinding.bind(mainView)

        binding.bottomNavigationView.setOnItemSelectedListener{
            when(it.itemId) {
                R.id.main -> {
                    navigateToMainFragment()
                    return@setOnItemSelectedListener true
                }
                R.id.compilation -> {
                    navigateToCompilationFragment()
                    return@setOnItemSelectedListener true
                }
                R.id.collection -> {
                    navigateToCollectionFragment()
                    return@setOnItemSelectedListener true
                }
                R.id.profile -> {
                    navigateToProfileFragment()
                    return@setOnItemSelectedListener true
                }
                else -> return@setOnItemSelectedListener true
            }
        }
        return binding.root
    }

    private fun navigateToProfileFragment() {

    }

    private fun navigateToCollectionFragment() {

    }

    private fun navigateToCompilationFragment() {

    }

    private fun navigateToMainFragment() {

    }
}