package com.example.cinema.presentation.movie.moviedetail

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.content.ContextCompat.getColor
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.cinema.R
import com.example.cinema.data.remote.api.dto.EpisodeDto
import com.example.cinema.data.remote.api.dto.TagDto
import com.example.cinema.databinding.FragmentMovieDetailBinding
import com.example.cinema.domain.model.AgeEnum
import com.example.cinema.domain.model.Movie
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {
    private lateinit var movieInfo: Movie
    private lateinit var binding: FragmentMovieDetailBinding
    private val viewModel: MovieDetailViewModel by viewModels()

    private var callback: MovieDetailListener? = null

    private lateinit var episodeId: String

    interface MovieDetailListener {
        fun backToMainFragment()
        fun navigateToEpisodeScreen(movieInfo: Movie)
        fun getMovieInfo(): Movie
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mainView = inflater.inflate(R.layout.fragment_movie_detail, container, false)
        binding = FragmentMovieDetailBinding.bind(mainView)

        val stateObserver = Observer<MovieDetailViewModel.MovieDetailState> {
            when (it) {
                MovieDetailViewModel.MovieDetailState.Loading -> {
                    binding.movieDetailProgressBar.show()
                    binding.movieDetailGroup.isGone = true
                }
                is MovieDetailViewModel.MovieDetailState.Success -> {
                    if (episodeId.isNotEmpty()) {
                        for (i in it.episodes.indices) {
                            if (it.episodes[i].episodeId == callback?.getMovieInfo()?.episodeId) {
                                navigateToEpisodeFragment(it.episodes[i], it.interval)
                                episodeId = ""
                                break
                            }
                        }
                    } else {
                        binding.movieDetailProgressBar.hide()
                        binding.movieDetailGroup.isGone = false
                        setOnBackButtonClickListener()
                        if (it.episodes.isNotEmpty()){
                            setOnWatchButtonClickListener(it.episodes[0], it.interval)
                        }
                        setOnChatButtonClickListener()
                        addCover(it.movieInfo.poster)
                        addAge(it.movieInfo.age)
                        addTags(it.movieInfo.tags)
                        addDescription(it.movieInfo.description)
                        addFrames(it.movieInfo.imageUrls)
                        addEpisodes(it.episodes, it.interval)
                    }
                }
                is MovieDetailViewModel.MovieDetailState.Failure -> {
                    binding.movieDetailProgressBar.hide()
                    binding.movieDetailGroup.isGone = false
                    createErrorDialog(it.errorMessage)
                }
            }
        }
        viewModel.state.observe(viewLifecycleOwner, stateObserver)

        return binding.root
    }

    override fun onStart() {
        movieInfo = callback?.getMovieInfo()!!
        viewModel.getMovieInfo(movieInfo)
        super.onStart()
    }

    override fun onAttach(context: Context) {
        callback = activity as MovieDetailListener

        episodeId = callback?.getMovieInfo()?.episodeId.toString()
        super.onAttach(context)
    }

    private fun setOnBackButtonClickListener() {
        binding.backButton.setOnClickListener {
            callback?.backToMainFragment()
        }
    }

    private fun setOnWatchButtonClickListener(episodeInfo: EpisodeDto, interval: String) {
        binding.movieDetailWatchButton.setOnClickListener {
            navigateToEpisodeFragment(episodeInfo, interval)
        }
    }

    private fun setOnChatButtonClickListener() {
        binding.chatButton.setOnClickListener {
            val chatInfo = callback?.getMovieInfo()?.chatInfo
            if (chatInfo != null) {
                val action =
                    MovieDetailFragmentDirections.actionMovieDetailFragmentToChatFragment(chatInfo)
                findNavController().navigate(action)
            }
        }
    }

    private fun addCover(imageUrl: String) {
        val imageView = binding.cover

        Glide.with(this).load(imageUrl).into(imageView)
    }

    private fun addAge(age: AgeEnum) {
        val textAge = binding.age
        textAge.text = getString(age.age)
        textAge.setTextColor(getColor(requireActivity(), age.color))
    }

    private fun addTags(tags: List<TagDto>) {
        binding.flexBox.removeAllViews()
        for (tag in tags) {
            val textView = TextView(context)
            textView.text = tag.tagName
            textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
            textView.background = getDrawable(requireActivity(), R.drawable.tag_background)
            textView.setTextColor(getColor(requireActivity(), R.color.white))

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
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        framesRecyclerView.adapter =
            FramesRecyclerAdapter(
                frames,
                requireActivity()
            )
    }

    private fun addEpisodes(episodes: List<EpisodeDto>, interval: String) {
        val episodesRecyclerView = binding.episodesRecyclerView

        episodesRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        episodesRecyclerView.adapter =
            EpisodesRecyclerAdapter(
                episodes
            ) { navigateToEpisodeFragment(it, interval) }
    }

    private fun createErrorDialog(message: String) {
        val builder = AlertDialog.Builder(context)

        builder.setTitle(getString(R.string.error))
        builder.setMessage(message)
        builder.show()
    }

    private fun navigateToEpisodeFragment(episodeInfo: EpisodeDto, interval: String) {
        val action =
            MovieDetailFragmentDirections.actionMovieDetailFragmentToEpisodeFragment(
                episodeInfo,
                interval
            )
        findNavController().navigate(action)
    }
}