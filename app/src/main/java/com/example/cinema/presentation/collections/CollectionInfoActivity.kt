package com.example.cinema.presentation.collections

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cinema.data.remote.dto.CollectionListItemDto
import com.example.cinema.data.remote.dto.MovieDto
import com.example.cinema.databinding.ActivityCollectionInfoBinding
import com.example.cinema.presentation.moviedetail.getSerializable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CollectionInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCollectionInfoBinding
    private val viewModel: CollectionInfoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCollectionInfoBinding.inflate(layoutInflater)

        val collectionInfo =
            getSerializable(this, "collectionInfo", CollectionListItemDto::class.java)
        viewModel.getInfo(collectionInfo)

        setOnClickListeners(collectionInfo)
        binding.collectionNameText.text = collectionInfo.name
        getCollectionInfo()
        setContentView(binding.root)
    }

    private fun setOnClickListeners(collectionInfo: CollectionListItemDto) {
        setOnClickEditButton(collectionInfo)
        setOnClickBackButton()
    }

    private fun setOnClickEditButton(collectionInfo: CollectionListItemDto) {
        binding.editButton.setOnClickListener {
            makeIntentToEditCollectionActivity(collectionInfo)
        }
    }

    private fun setOnClickBackButton() {
        binding.backCollectionsButton.setOnClickListener {
            finish()
        }
    }



    private fun getCollectionInfo() {
        val collectionObserver = Observer<List<MovieDto>> { newList ->
            if (newList.isNotEmpty()) {
                addCollectionInfo(newList)
            }
        }
        viewModel.collectionInfoList.observe(this, collectionObserver)
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
}