package com.example.cinema.presentation.movie.episode

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.VideoView
import androidx.fragment.app.Fragment
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.SimpleExoPlayer
import androidx.media3.ui.PlayerView
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.cinema.R
import com.example.cinema.databinding.FragmentEpisodeBinding
import com.example.cinema.presentation.movie.moviedetail.MovieDetailFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EpisodeFragment: Fragment() {
    private lateinit var binding: FragmentEpisodeBinding
    private val args: EpisodeFragmentArgs by navArgs()

    private var callback: MovieDetailFragment.MovieDetailListener? = null

    private lateinit var videoView: PlayerView
    private lateinit var mediaController: MediaController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mainView = inflater.inflate(R.layout.fragment_episode, container, false)
        binding = FragmentEpisodeBinding.bind(mainView)

        return binding.root
    }

    override fun onAttach(context: Context) {
        callback = activity as MovieDetailFragment.MovieDetailListener
        super.onAttach(context)
    }

    override fun onStart() {
        addEpisodeInfo()
        addVideoView()
        super.onStart()
    }

    private fun addEpisodeInfo() {
        binding.episodeNameText.text = args.episodeInfo.name
        binding.movieName.text = callback?.getMovieInfo()?.name
        Glide.with(binding.movieCover).load(callback?.getMovieInfo()?.poster).into(binding.movieCover)
        binding.movieInfo.text = args.episodeInfo.director
        binding.movieYears.text = args.episodeInfo.year
        binding.episodeDescriptionText.text = args.episodeInfo.description
    }

    private fun addVideoView() {
        val exoPlayer = ExoPlayer.Builder(requireContext()).build()
        videoView = binding.episodeVideoView
        videoView.player = exoPlayer

        val mediaItem = MediaItem.fromUri(Uri.parse(args.episodeInfo.filePath))
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()
        exoPlayer.play()
    }

}