package com.example.cinema.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.cinema.R
import com.example.cinema.databinding.BottomNavBarBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

@Suppress("DEPRECATION")
class BottomNavigationFragment : Fragment() {

    private lateinit var binding: BottomNavBarBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mainView = inflater.inflate(R.layout.bottom_nav_bar, container, false)
        binding = BottomNavBarBinding.bind(mainView)

        val navHostFragment =
            requireActivity().supportFragmentManager.findFragmentById(R.id.fragmentBottomBar) as NavHostFragment?
        val navController = navHostFragment?.navController
        val navView: BottomNavigationView = binding.bottomNavigationView

        if (navController != null) {
            navView.setupWithNavController(navController)
        }

        return binding.root
    }
}