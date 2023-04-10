package com.example.cinema.presentation.movie.episode

import android.app.AlertDialog
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.VideoView
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.SimpleExoPlayer
import androidx.media3.ui.PlayerView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.cinema.R
import com.example.cinema.databinding.FragmentEpisodeBinding
import com.example.cinema.presentation.bottomnavigation.profile.ProfileViewModel
import com.example.cinema.presentation.movie.moviedetail.MovieDetailFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EpisodeFragment: Fragment() {
    private lateinit var binding: FragmentEpisodeBinding
    private val args: EpisodeFragmentArgs by navArgs()
    private val viewModel: EpisodeViewModel by viewModels()

    private var callback: MovieDetailFragment.MovieDetailListener? = null

    private lateinit var videoView: PlayerView
    private lateinit var exoPlayer: ExoPlayer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mainView = inflater.inflate(R.layout.fragment_episode, container, false)
        binding = FragmentEpisodeBinding.bind(mainView)

        val stateObserver = Observer<EpisodeViewModel.EpisodeState> {
            when (it) {
                EpisodeViewModel.EpisodeState.Loading -> {
                    binding.episodeProgressBar.show()
                }
                EpisodeViewModel.EpisodeState.Success -> {
                    binding.episodeProgressBar.hide()
                }
                is EpisodeViewModel.EpisodeState.Failure -> {
                    binding.episodeProgressBar.hide()
                    createErrorDialog(it.errorMessage)
                }
            }
        }
        viewModel.state.observe(viewLifecycleOwner, stateObserver)

        binding.startPlayButton.setOnClickListener {
            binding.previewBackground.visibility = View.INVISIBLE
            binding.preview.visibility = View.INVISIBLE
            binding.startPlayButton.visibility = View.INVISIBLE
            exoPlayer.play()
        }

        return binding.root
    }

    override fun onAttach(context: Context) {
        callback = activity as MovieDetailFragment.MovieDetailListener
        super.onAttach(context)
    }
    @androidx.media3.common.util.UnstableApi
    override fun onStart() {
        addEpisodeInfo()
        addVideoView()
        setPreview()
        setOnButtonsClickListener()
        super.onStart()
    }

    private fun setOnButtonsClickListener() {
        setOnChatButtonListener()
        setOnAddInCollectionButtonListener()
        setOnAddInFavoritesButtonListener()
        setOnBackButtonClickListener()
    }

    private fun setOnChatButtonListener() {
        binding.episodeChatButton.setOnClickListener {

        }
    }
    private fun setOnAddInCollectionButtonListener() {
        binding.addInCollectionButton.setOnClickListener {

        }
    }
    private fun setOnAddInFavoritesButtonListener() {
        binding.addInFavoritesButton.setOnClickListener {
            viewModel.addMovieInFavorites(callback?.getMovieInfo()!!.movieId)
        }
    }

    private fun setOnBackButtonClickListener() {
        binding.episodeBackButton.setOnClickListener {
            findNavController().navigate(R.id.action_episodeFragment_to_movieDetailFragment)
        }
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
        exoPlayer = ExoPlayer.Builder(requireContext()).build()
        videoView = binding.episodeVideoView
        videoView.player = exoPlayer

        val mediaItem = MediaItem.fromUri(Uri.parse(args.episodeInfo.filePath))
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()
    }
    @androidx.media3.common.util.UnstableApi
    private fun setPreview() {
        val image = binding.preview
        Glide.with(image).load(args.episodeInfo.preview).into(image)

        binding.previewBackground.visibility = View.VISIBLE
        image.visibility = View.VISIBLE
        binding.startPlayButton.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        exoPlayer.pause()
        super.onDestroy()
    }

    private fun createErrorDialog(message: String) {
        val builder = AlertDialog.Builder(context)

        builder.setTitle(getString(R.string.error))
        builder.setMessage(message)
        builder.show()
    }

}