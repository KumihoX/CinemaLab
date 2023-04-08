package com.example.cinema.presentation.collections

import android.app.AlertDialog
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.cinema.R
import com.example.cinema.databinding.ActivityCreateCollectionBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateCollectionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateCollectionBinding
    private val viewModel: CreateCollectionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateCollectionBinding.inflate(layoutInflater)

        val stateObserver = Observer<CreateCollectionViewModel.CreateCollectionState> {
            when (it) {
                CreateCollectionViewModel.CreateCollectionState.Loading -> {
                    binding.createCollectionProgressBar.show()
                }
                CreateCollectionViewModel.CreateCollectionState.Initial -> {
                    binding.createCollectionProgressBar.hide()
                    setOnClickButtons()
                }
                is CreateCollectionViewModel.CreateCollectionState.Success -> {
                    binding.createCollectionProgressBar.hide()
                    finish()
                }
                is CreateCollectionViewModel.CreateCollectionState.Failure -> {
                    binding.createCollectionProgressBar.hide()
                    createErrorDialog(it.errorMessage)
                }
            }
        }
        viewModel.state.observe(this, stateObserver)

        setContentView(binding.root)
    }

    private fun setOnClickButtons() {
        setOnClickSaveButton()
        setOnClickBackButton()
    }

    private fun setOnClickSaveButton() {
        binding.saveCollectionButton.setOnClickListener {
            viewModel.postCollection(binding.nameCollectionsEditText.text.toString())
        }
    }

    private fun setOnClickBackButton() {
        binding.backCollectionsButton.setOnClickListener {
            finish()
        }
    }

    private fun createErrorDialog(message: String) {
        val builder = AlertDialog.Builder(this)

        builder.setTitle(getString(R.string.error))
        builder.setMessage(message)
        builder.show()
    }
}