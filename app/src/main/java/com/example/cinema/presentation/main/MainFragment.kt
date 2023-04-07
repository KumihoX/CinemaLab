package com.example.cinema.presentation.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.example.cinema.presentation.moviedetail.MovieDetailActivity
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

        return binding.root
    }

    override fun onStart() {
        viewModel.getMainScreenInfo()
        getCover()
        getTrends()
        getYouWatched()
        getNews()
        getForYou()
        super.onStart()
    }

    private fun getCover() {
        val coverObserver = Observer<String> { newState ->
            if (newState.isNotEmpty()) {
                addCover(newState)
            }
        }
        viewModel.cover.observe(viewLifecycleOwner, coverObserver)
    }

    private fun getTrends() {
        val trendsListObserver = Observer<List<Movie>> { newList ->
            if (newList.isNotEmpty()) {
                addTrends(newList)
            }
        }
        viewModel.inTrendsList.observe(viewLifecycleOwner, trendsListObserver)
    }

    private fun getYouWatched() {
        val youWatchedListObserver = Observer<String> { newState ->
            if (newState.isNotEmpty()) {
                addYouWatched(newState)
            }
        }
        viewModel.youWatchedCover.observe(viewLifecycleOwner, youWatchedListObserver)
    }

    private fun getNews() {
        val newListObserver = Observer<List<Movie>> { newList ->
            if (newList.isNotEmpty()) {
                addNews(newList)
            }
        }
        viewModel.newList.observe(viewLifecycleOwner, newListObserver)
    }

    private fun getForYou() {
        val forYouListObserver = Observer<List<Movie>> { newList ->
            if (newList.isNotEmpty()) {
                addForYou(newList)
            }
        }
        viewModel.forYouList.observe(viewLifecycleOwner, forYouListObserver)

    }

    private fun addCover(imageUrl: String) {
        val imageView = binding.cover

        Glide.with(this).load(imageUrl).into(imageView)
    }

    private fun addTrends(trendsList: List<Movie>) {
        val inTrendText = binding.inTrendText
        inTrendText.visibility = View.VISIBLE

        val inTrendRecyclerView = binding.inTrendRecyclerView
        inTrendRecyclerView.visibility = View.VISIBLE

        inTrendRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        inTrendRecyclerView.adapter =
            CustomRecyclerAdapter(
                trendsList,
                this,
                R.layout.vertical_item,
                findNavController()
            ) { makeIntentToMovieInfoActivity(it) }
    }

    private fun addYouWatched(cover: String) {
        val youWatchedText = binding.youWatchedText
        youWatchedText.visibility = View.VISIBLE

        val filmName = binding.filmName
        filmName.text = viewModel.youWatchedText.value

        val youWatchedCard = binding.youWatchedCard
        youWatchedCard.visibility = View.VISIBLE

        val youWatchedCover = binding.youWatchedCover
        Glide.with(this).load(cover).into(youWatchedCover)
    }

    private fun addNews(newsList: List<Movie>) {
        val newText = binding.newFilmText
        newText.visibility = View.VISIBLE

        val newRecyclerView = binding.newRecyclerView
        newRecyclerView.visibility = View.VISIBLE

        newRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        newRecyclerView.adapter =
            CustomRecyclerAdapter(
                newsList,
                this,
                R.layout.horizontal_item,
                findNavController()
            ) { makeIntentToMovieInfoActivity(it) }
    }

    private fun addForYou(forYouList: List<Movie>) {
        val forYouText = binding.forYouText
        forYouText.visibility = View.VISIBLE

        val forYouRecyclerView = binding.forYouRecyclerView
        forYouRecyclerView.visibility = View.VISIBLE

        forYouRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        forYouRecyclerView.adapter =
            CustomRecyclerAdapter(
                forYouList,
                this,
                R.layout.vertical_item,
                findNavController()
            ) { makeIntentToMovieInfoActivity(it) }
    }

    private fun makeIntentToMovieInfoActivity(movieInfo: Movie) {
        val intent = Intent(activity, MovieDetailActivity::class.java)
        activity?.overridePendingTransition(0, 0)
        intent.putExtra("movieInfo", movieInfo)
        startActivity(intent)
    }


}