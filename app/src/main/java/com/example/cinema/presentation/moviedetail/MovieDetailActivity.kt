package com.example.cinema.presentation.moviedetail

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.cinema.R
import com.example.cinema.data.remote.dto.MovieDto
import com.example.cinema.databinding.ActivityMovieDetailBinding
import java.io.Serializable

@Suppress("DEPRECATION")
class MovieDetailActivity : AppCompatActivity() {

    private lateinit var movieInfo: MovieDto

    private lateinit var binding: ActivityMovieDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)

        movieInfo = getSerializable(this, "movieInfo", MovieDto::class.java)
        binding.movieName.text = movieInfo.name

        setContentView(binding.root)
    }
}