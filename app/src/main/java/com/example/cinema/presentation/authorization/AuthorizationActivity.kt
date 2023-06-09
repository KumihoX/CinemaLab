package com.example.cinema.presentation.authorization

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cinema.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthorizationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Cinema)
        setContentView(R.layout.activity_main)
    }
}