package com.example.cinema.presentation.collections

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.cinema.R
import com.example.cinema.data.remote.dto.CollectionListItemDto
import com.example.cinema.databinding.ActivityEditCollectionBinding
import com.example.cinema.presentation.moviedetail.getSerializable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditCollectionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditCollectionBinding
    private val viewModel: EditCollectionViewModel by viewModels()

    private val collectionInfo =
        getSerializable(this, "collectionInfo", CollectionListItemDto::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditCollectionBinding.inflate(layoutInflater)

        val stateObserver = Observer<EditCollectionViewModel.EditCollectionState> {
            when (it) {
                EditCollectionViewModel.EditCollectionState.Loading -> {
                    binding.editCollectionProgressBar.show()
                }
                EditCollectionViewModel.EditCollectionState.Initial -> {
                    binding.editCollectionProgressBar.hide()
                    setOnClickButtons()
                }
                is EditCollectionViewModel.EditCollectionState.Success -> {
                    binding.editCollectionProgressBar.hide()
                    finish()
                }
                is EditCollectionViewModel.EditCollectionState.Failure -> {
                    binding.editCollectionProgressBar.hide()
                    createErrorDialog(it.errorMessage)
                }
            }
        }
        viewModel.state.observe(this, stateObserver)

        binding.nameCollectionsEditText.setText(collectionInfo.name)

        setContentView(binding.root)
    }

    private fun setOnClickButtons() {
        setOnBackClickListener()
        setOnSaveButtonClickListener()
        setOnDeleteButtonClickListener()
    }

    private fun setOnBackClickListener() {
        binding.backCollectionsButton.setOnClickListener {
            finish()
        }
    }

    private fun setOnSaveButtonClickListener() {
        binding.saveCollectionButton.setOnClickListener {

        }
    }
    private fun setOnDeleteButtonClickListener() {
        binding.deleteButton.setOnClickListener {
            viewModel.deleteCollection(collectionId = collectionInfo.collectionId)
        }
    }

    private fun createErrorDialog(message: String) {
        val builder = AlertDialog.Builder(this)

        builder.setTitle(getString(R.string.error))
        builder.setMessage(message)
        builder.show()
    }
}