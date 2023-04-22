package com.example.cinema.presentation.movie

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.navArgs
import com.example.cinema.R
import com.example.cinema.databinding.ActivityMovieDetailBinding
import com.example.cinema.domain.model.Movie
import com.example.cinema.presentation.movie.moviedetail.MovieDetailFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MovieDetailActivity : AppCompatActivity(),
    MovieDetailFragment.MovieDetailListener {

    private lateinit var binding: ActivityMovieDetailBinding
    private val args: MovieDetailActivityArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Cinema)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }

    override fun navigateToEpisodeScreen(movieInfo: Movie) {

    }

    override fun getMovieInfo(): Movie {
        return args.movieInfo
    }

    override fun backToMainFragment() {
        finish()
    }
}