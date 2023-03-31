package com.example.cinema.presentation.moviedetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cinema.R
import com.example.cinema.data.remote.dto.MovieDto
import com.example.cinema.databinding.ActivityMovieDetailBinding

class MovieDetailActivity : AppCompatActivity() {

    private var movieInfo: MovieDto? = null

    private lateinit var binding: ActivityMovieDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*movieInfo = intent.getSerializableExtra("serialzable") as MovieDto?
        val name: String = movieInfo?.name.toString()
        val id: String = movieInfo?.movieId.toString()*/

        val text = binding.textView
        //text.text = name

        setContentView(R.layout.activity_movie_detail)
    }
}