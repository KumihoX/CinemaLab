package com.example.cinema.presentation.bottomnavigation.collections.detail

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cinema.R
import com.example.cinema.data.remote.dto.CollectionListItemDto
import com.example.cinema.data.remote.dto.MovieDto
import com.example.cinema.databinding.ActivityCollectionInfoBinding
import com.example.cinema.presentation.bottomnavigation.collections.change.EditCollectionActivity
import com.example.cinema.presentation.movie.getSerializable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CollectionInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCollectionInfoBinding
    private val viewModel: CollectionInfoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCollectionInfoBinding.inflate(layoutInflater)

        val stateObserver = Observer<CollectionInfoViewModel.CollectionInfoState> {
            when (it) {
                CollectionInfoViewModel.CollectionInfoState.Loading -> {
                    binding.collectionInfoProgressBar.show()
                    binding.collectionInfoGroup.isGone = true
                }
                is CollectionInfoViewModel.CollectionInfoState.Success -> {
                    binding.collectionInfoProgressBar.hide()
                    binding.collectionInfoGroup.isGone = false

                    setOnClickListeners(it.collectionInfo, it.isFavorite)
                    binding.collectionNameText.text = it.collectionInfo.name
                    addCollectionInfo(it.moviesInCollection)
                }
                is CollectionInfoViewModel.CollectionInfoState.Failure -> {
                    binding.collectionInfoProgressBar.hide()
                    binding.collectionInfoGroup.isGone = false
                    createErrorDialog(it.errorMessage)
                }
            }
        }
        viewModel.state.observe(this, stateObserver)

        setContentView(binding.root)
    }

    override fun onStart() {
        val collectionInfo =
            getSerializable(this, "collectionInfo", CollectionListItemDto::class.java)
        viewModel.getInfo(collectionInfo)
        super.onStart()
    }

    private fun setOnClickListeners(collectionInfo: CollectionListItemDto, isFavorite: Boolean) {
        setOnClickEditButton(collectionInfo, isFavorite)
        setOnClickBackButton()
    }

    private fun setOnClickEditButton(collectionInfo: CollectionListItemDto, isFavorite: Boolean) {
        if (isFavorite) {
            binding.editButton.isGone = true
            return
        }
        binding.editButton.setOnClickListener {
            makeIntentToEditCollectionActivity(collectionInfo)
        }
    }

    private fun setOnClickBackButton() {
        binding.backCollectionsButton.setOnClickListener {
            finish()
        }
    }


    private fun addCollectionInfo(collectionInfo: List<MovieDto>) {
        val collectionInfoRecyclerView = binding.collectionInfoRecyclerView

        collectionInfoRecyclerView.layoutManager =
            LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        collectionInfoRecyclerView.adapter =
            CollectionInfoRecyclerAdapter(
                collectionInfo
            )
    }

    private fun makeIntentToEditCollectionActivity(collectionInfo: CollectionListItemDto) {
        val intent = Intent(this, EditCollectionActivity::class.java)
        this.overridePendingTransition(0, 0)
        intent.putExtra("collectionInfo", collectionInfo)
        startActivity(intent)
    }

    private fun createErrorDialog(message: String) {
        val builder = AlertDialog.Builder(this)

        builder.setTitle(getString(R.string.incorrect_input_data))
        builder.setMessage(message)
        builder.show()
    }
}