package com.example.cinema.presentation.main

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideModule
import com.example.cinema.R
import com.example.cinema.databinding.FragmentMainBinding
import com.example.cinema.domain.model.Movie
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@GlideModule
class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mainView = inflater.inflate(R.layout.fragment_main, container, false)
        binding = FragmentMainBinding.bind(mainView)
        val stateObserver = Observer<MainViewModel.MainState> {
            when (it) {
                MainViewModel.MainState.Loading -> {
                    binding.mainProgressBar.show()
                    binding.mainGroup.isGone = true
                }
                is MainViewModel.MainState.Success -> {
                    binding.mainProgressBar.hide()
                    binding.mainGroup.isGone = false
                    addCover(it.cover)
                    addTrends(it.inTrend)
                    addYouWatched(it.youWatchedCover)
                    addNews(it.newList)
                    addForYou(it.forYou)
                }
                is MainViewModel.MainState.Failure -> {
                    binding.mainProgressBar.hide()
                    binding.mainGroup.isGone = false
                    createErrorDialog(it.errorMessage)
                }
            }
        }
        viewModel.state.observe(viewLifecycleOwner, stateObserver)

        return binding.root
    }

    override fun onStart() {
        viewModel.getMainScreenInfo()
        super.onStart()
    }

    private fun addCover(imageUrl: String) {
        val imageView = binding.cover

        Glide.with(this).load(imageUrl).into(imageView)
    }

    private fun addTrends(trendsList: List<Movie>) {
        if (trendsList.isNotEmpty()) {
            binding.inTrendText.visibility = View.VISIBLE

            val inTrendRecyclerView = binding.inTrendRecyclerView
            inTrendRecyclerView.visibility = View.VISIBLE

            inTrendRecyclerView.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            inTrendRecyclerView.adapter =
                CustomRecyclerAdapter(
                    trendsList,
                    R.layout.vertical_item,
                    findNavController()
                ) { navigateToMovieInfoActivity(it) }
        } else {
            binding.inTrendText.visibility = View.GONE
            binding.inTrendRecyclerView.visibility = View.GONE
        }
    }

    private fun addYouWatched(youWatchedCover: List<Movie>) {
        if (youWatchedCover.isNotEmpty()) {
            val image = youWatchedCover[0].poster
            val youWatchedText = binding.youWatchedText
            youWatchedText.visibility = View.VISIBLE

            val filmName = binding.filmName
            filmName.text = youWatchedCover[0].name

            val youWatchedCard = binding.youWatchedCard
            youWatchedCard.visibility = View.VISIBLE

            val youWatchedCover = binding.youWatchedCover
            Glide.with(this).load(image).into(youWatchedCover)
        } else {
            binding.youWatchedText.visibility = View.GONE
            binding.youWatchedCard.visibility = View.GONE
        }
    }

    private fun addNews(newsList: List<Movie>) {
        if (newsList.isNotEmpty()) {
            val newText = binding.newFilmText
            newText.visibility = View.VISIBLE

            val newRecyclerView = binding.newRecyclerView
            newRecyclerView.visibility = View.VISIBLE

            newRecyclerView.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            newRecyclerView.adapter =
                CustomRecyclerAdapter(
                    newsList,
                    R.layout.horizontal_item,
                    findNavController()
                ) { navigateToMovieInfoActivity(it) }
        } else {
            binding.newFilmText.visibility = View.GONE
            binding.newRecyclerView.visibility = View.GONE
        }
    }

    private fun addForYou(forYouList: List<Movie>) {
        if (forYouList.isNotEmpty()) {
            val forYouText = binding.forYouText
            forYouText.visibility = View.VISIBLE

            val forYouRecyclerView = binding.forYouRecyclerView
            forYouRecyclerView.visibility = View.VISIBLE

            forYouRecyclerView.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            forYouRecyclerView.adapter =
                CustomRecyclerAdapter(
                    forYouList,
                    R.layout.vertical_item,
                    findNavController()
                ) { navigateToMovieInfoActivity(it) }
        } else {
            binding.forYouText.visibility = View.GONE
            binding.forYouRecyclerView.visibility = View.GONE
        }
    }

    private fun navigateToMovieInfoActivity(movieInfo: Movie) {
        val action = MainFragmentDirections.actionMainToMovieDetailActivity(movieInfo)
        findNavController().navigate(action)
    }

    private fun createErrorDialog(message: String) {
        val builder = AlertDialog.Builder(context)

        builder.setTitle(getString(R.string.error))
        builder.setMessage(message)
        builder.show()
    }
}