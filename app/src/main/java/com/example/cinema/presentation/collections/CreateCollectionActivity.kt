package com.example.cinema.presentation.collections

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.example.cinema.R
import com.example.cinema.databinding.ActivityCreateCollectionBinding
import com.example.cinema.databinding.ActivityMovieDetailBinding
import com.example.cinema.domain.model.Movie
import com.example.cinema.presentation.moviedetail.getSerializable
import com.example.cinema.presentation.signup.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateCollectionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateCollectionBinding
    private val viewModel: CreateCollectionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateCollectionBinding.inflate(layoutInflater)

        setOnClickButtons()

        setContentView(binding.root)
    }

    private fun setOnClickButtons() {
        setOnClickSaveButton()
        setOnClickBackButton()
    }

    private fun setOnClickSaveButton() {
        binding.saveCollectionButton.setOnClickListener {
            viewModel.postCollection(binding.nameCollectionsEditText.text.toString())
            finish()
        }
    }

    private fun setOnClickBackButton() {
        binding.backCollectionsButton.setOnClickListener {
            finish()
        }
    }
}