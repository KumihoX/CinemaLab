package com.example.cinema.presentation.moviedetail

import android.os.Bundle
import android.text.Layout.Alignment
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
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
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MovieDetailActivity : AppCompatActivity() {

    private lateinit var movieInfo: Movie
    private lateinit var binding: ActivityMovieDetailBinding
    private val viewModel: MovieDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)

        movieInfo = getSerializable(this, "movieInfo", Movie::class.java)
        viewModel.getMovieInfo(movieInfo)

        getCover()
        getAge()
        getTags()
        getDescription()
        getFrames()
        getEpisodes()

        setContentView(binding.root)
    }

    private fun getCover() {
        val coverObserver = Observer<String> { newState ->
            if (newState.isNotEmpty()) {
                addCover(newState)
            }
        }
        viewModel.poster.observe(this, coverObserver)
    }

    private fun getAge() {
        val ageObserver = Observer<AgeEnum> { newState ->
            addAge(newState)
        }
        viewModel.age.observe(this, ageObserver)
    }

    private fun getTags() {
        val tagsObserver = Observer<List<TagDto>> { newList ->
            if (newList.isNotEmpty()) {
                addTags(newList)
            }
        }
        viewModel.tags.observe(this, tagsObserver)
    }

    private fun getDescription() {
        val descriptionObserver = Observer<String> { newState ->
            if (newState.isNotEmpty()) {
                addDescription(newState)
            }
        }
        viewModel.description.observe(this, descriptionObserver)
    }

    private fun getFrames() {
        val framesObserver = Observer<List<String>> { newList ->
            if (newList.isNotEmpty()) {
                addFrames(newList)
            }
        }
        viewModel.frames.observe(this, framesObserver)
    }

    private fun getEpisodes() {
        val episodesObserver = Observer<List<EpisodeDto>> { newList ->
            if (newList.isNotEmpty()) {
                addEpisodes(newList)
            }
        }
        viewModel.episodes.observe(this, episodesObserver)
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
}