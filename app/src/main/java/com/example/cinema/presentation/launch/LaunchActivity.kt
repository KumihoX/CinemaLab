package com.example.cinema.presentation.launch

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.cinema.R
import com.example.cinema.databinding.ActivityLaunchBinding
import com.example.cinema.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LaunchActivity: AppCompatActivity() {
    private lateinit var binding: ActivityLaunchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Cinema)
        binding = ActivityLaunchBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }
}