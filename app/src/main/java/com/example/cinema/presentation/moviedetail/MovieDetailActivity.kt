package com.example.cinema.presentation.moviedetail

import android.app.AlertDialog
import android.os.Bundle
import android.text.Layout.Alignment
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.setPadding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.cinema.R
import com.example.cinema.data.remote.dto.EpisodeDto
import com.example.cinema.data.remote.dto.MovieDto
import com.example.cinema.data.remote.dto.TagDto
import com.example.cinema.databinding.ActivityMovieDetailBinding
import com.example.cinema.domain.model.AgeEnum
import com.example.cinema.domain.model.Movie
import com.example.cinema.presentation.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MovieDetailActivity : AppCompatActivity() {

    private lateinit var movieInfo: Movie
    private lateinit var binding: ActivityMovieDetailBinding
    private val viewModel: MovieDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)

        val stateObserver = Observer<MovieDetailViewModel.MovieDetailState> {
            when (it) {
                MovieDetailViewModel.MovieDetailState.Loading -> {
                    binding.movieDetailProgressBar.show()
                    binding.movieDetailGroup.isGone = true
                }
                is MovieDetailViewModel.MovieDetailState.Success -> {
                    binding.movieDetailProgressBar.hide()
                    binding.movieDetailGroup.isGone = false
                    addCover(it.movieInfo.poster)
                    addAge(it.movieInfo.age)
                    addTags(it.movieInfo.tags)
                    addDescription(it.movieInfo.description)
                    addFrames(it.movieInfo.imageUrls)
                    addEpisodes(it.episodes)
                }
                is MovieDetailViewModel.MovieDetailState.Failure -> {
                    binding.movieDetailProgressBar.hide()
                    binding.movieDetailGroup.isGone = false
                    createErrorDialog(it.errorMessage)
                }
            }
        }
        viewModel.state.observe(this, stateObserver)

        setContentView(binding.root)
    }

    override fun onStart() {
        movieInfo = getSerializable(this, "movieInfo", Movie::class.java)
        viewModel.getMovieInfo(movieInfo)
        super.onStart()
    }

    private fun addCover(imageUrl: String) {
        val imageView = binding.cover

        Glide.with(this).load(imageUrl).into(imageView)
    }

    private fun addAge(age: AgeEnum) {
        val textAge = binding.age
        textAge.text = getString(age.age)
        textAge.setTextColor(getColor(age.color))
    }

    private fun addTags(tags: List<TagDto>) {
        binding.flexBox.removeAllViews()
        for (tag in tags) {
            val textView = TextView(this)
            textView.text = tag.tagName
            textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
            textView.background = getDrawable(R.drawable.tag_background)
            textView.setTextColor(getColor(R.color.white))

            binding.flexBox.addView(textView)

            val params = textView.layoutParams as ViewGroup.MarginLayoutParams
            params.marginEnd = 16
            params.bottomMargin = 16
        }

    }

    private fun addDescription(description: String) {
        binding.descriptionText.text = description
    }

    private fun addFrames(frames: List<String>) {
        val framesRecyclerView = binding.framesRecyclerView

        framesRecyclerView.layoutManager =
            LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
        framesRecyclerView.adapter =
            FramesRecyclerAdapter(
                frames,
                this
            )
    }

    private fun addEpisodes(episodes: List<EpisodeDto>) {
        val episodesRecyclerView = binding.episodesRecyclerView

        episodesRecyclerView.layoutManager =
            LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        episodesRecyclerView.adapter =
            EpisodesRecyclerAdapter(
                episodes,
                this
            )
    }

    private fun createErrorDialog(message: String) {
        val builder = AlertDialog.Builder(this)

        builder.setTitle(getString(R.string.error))
        builder.setMessage(message)
        builder.show()
    }
}