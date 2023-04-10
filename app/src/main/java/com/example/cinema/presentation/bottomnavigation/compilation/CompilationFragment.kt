package com.example.cinema.presentation.bottomnavigation.compilation

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.cinema.R
import com.example.cinema.data.remote.dto.MovieDto
import com.example.cinema.databinding.FragmentCompilationBinding
import com.yuyakaido.android.cardstackview.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CompilationFragment : Fragment(), CardStackListener {
    private lateinit var binding: FragmentCompilationBinding
    private lateinit var layoutManager: CardStackLayoutManager

    private val viewModel: CompilationViewModel by viewModels()
    private lateinit var compilationList: List<MovieDto>
    private var currentPosition = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mainView = inflater.inflate(R.layout.fragment_compilation, container, false)
        binding = FragmentCompilationBinding.bind(mainView)

        val stateObserver = Observer<CompilationViewModel.CompilationState> {
            when (it) {
                CompilationViewModel.CompilationState.FirstLoading -> {
                    binding.compilationProgressBar.show()
                    binding.compilationGroup.isGone = true
                }
                CompilationViewModel.CompilationState.Loading -> {
                    binding.compilationProgressBar.show()
                }
                CompilationViewModel.CompilationState.Continue -> {
                    binding.compilationProgressBar.hide()
                }
                is CompilationViewModel.CompilationState.Success -> {
                    binding.compilationProgressBar.hide()
                    if (it.compilation.isNotEmpty()) {
                        binding.compilationGroup.isGone = false
                        compilationList = it.compilation
                        createCardStackView(it.compilation)
                        setOnButtonsClickListeners()
                    } else {
                        binding.compilationBackgroundGroup.isGone = false
                    }
                }
                is CompilationViewModel.CompilationState.Failure -> {
                    binding.compilationProgressBar.hide()
                    binding.compilationGroup.isGone = false
                    createErrorDialog(it.errorMessage)
                }
            }
        }
        viewModel.state.observe(viewLifecycleOwner, stateObserver)

        return binding.root
    }

    override fun onStart() {
        viewModel.getCompilation()

        super.onStart()
    }

    private fun createCardStackView(compilation: List<MovieDto>) {
        layoutManager = CardStackLayoutManager(context, this).apply {
            setMaxDegree(-10.0f)
        }
        binding.cardStackView.layoutManager = layoutManager
        binding.cardStackView.adapter = CardStackViewAdapter(compilation, this)
    }

    private fun setOnButtonsClickListeners() {
        setOnLikeButtonClickListener()
        setOnPlayButtonClickListener()
        setOnDislikeButtonClickListener()
    }

    private fun setOnLikeButtonClickListener() {
        binding.likeButton.setOnClickListener {
            val swipeSetting = SwipeAnimationSetting.Builder()
                .setDirection(Direction.Right)
                .setDuration(Duration.Normal.duration)
                .setInterpolator(LinearInterpolator())
                .build()
            layoutManager.setSwipeAnimationSetting(swipeSetting)
            binding.cardStackView.swipe()
        }
    }

    private fun setOnPlayButtonClickListener() {
        binding.playCompilationButton.setOnClickListener {

        }
    }

    private fun setOnDislikeButtonClickListener() {
        binding.dislikeButton.setOnClickListener {
            val swipeSetting = SwipeAnimationSetting.Builder()
                .setDirection(Direction.Left)
                .setDuration(Duration.Normal.duration)
                .setInterpolator(LinearInterpolator())
                .build()
            layoutManager.setSwipeAnimationSetting(swipeSetting)
            binding.cardStackView.swipe()
        }
    }

    override fun onCardDragging(direction: Direction?, ratio: Float) {

    }

    override fun onCardSwiped(direction: Direction?) {
        when (direction) {
            Direction.Left -> {
                viewModel.dislike(compilationList[currentPosition].movieId)
            }
            Direction.Right -> {
                viewModel.like(compilationList[currentPosition].movieId)
                viewModel.dislike(compilationList[currentPosition].movieId)
            }
            else -> {}
        }
    }

    override fun onCardRewound() {

    }

    override fun onCardCanceled() {

    }

    override fun onCardAppeared(view: View?, position: Int) {
        binding.cardStackFilmName.text = compilationList[position].name
        currentPosition++
    }

    override fun onCardDisappeared(view: View?, position: Int) {
        if (position == compilationList.size - 1) {
            binding.compilationGroup.isGone = true
            binding.compilationBackgroundGroup.isGone = false
        }
    }

    private fun createErrorDialog(message: String) {
        val builder = AlertDialog.Builder(context)

        builder.setTitle(getString(R.string.error))
        builder.setMessage(message)
        builder.show()
    }
}