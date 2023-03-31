package com.example.cinema.presentation

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.cinema.R
import com.example.cinema.databinding.ActivityBottomNavigationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BottomNavigationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBottomNavigationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBottomNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        val navView: BottomNavigationView = binding.bottomNavigationView

        val navController = findNavController(R.id.fragmentBottomBar)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.main, R.id.compilation, R.id.collection, R.id.profile
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}