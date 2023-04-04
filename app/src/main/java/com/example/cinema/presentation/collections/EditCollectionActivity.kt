package com.example.cinema.presentation.collections

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import androidx.activity.viewModels
import com.example.cinema.R
import com.example.cinema.data.remote.dto.CollectionListItemDto
import com.example.cinema.databinding.ActivityCollectionInfoBinding
import com.example.cinema.databinding.ActivityEditCollectionBinding
import com.example.cinema.presentation.moviedetail.getSerializable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditCollectionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditCollectionBinding
    private val viewModel: EditCollectionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditCollectionBinding.inflate(layoutInflater)

        val collectionInfo =
            getSerializable(this, "collectionInfo", CollectionListItemDto::class.java)

        binding.nameCollectionsEditText.setText(collectionInfo.name)
        binding.backCollectionsButton.setOnClickListener {
            finish()
        }

        binding.deleteButton.setOnClickListener {
            viewModel.deleteCollection(collectionId = collectionInfo.collectionId)
            finish()
        }

        setContentView(binding.root)
    }
}