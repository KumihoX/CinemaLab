package com.example.cinema.presentation.bottomnavigation.collections.change.create

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navArgs
import com.example.cinema.R
import com.example.cinema.databinding.FragmentCreateCollectionBinding
import com.example.cinema.presentation.movie.MovieDetailActivityArgs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateCollectionFragment: Fragment() {
    private lateinit var binding: FragmentCreateCollectionBinding
    private val viewModel: CreateCollectionViewModel by viewModels()

    private val args: CreateCollectionFragmentArgs by navArgs()

    private var callback: CreateCollectionListener? = null

    interface CreateCollectionListener {
        fun backToCollectionsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mainView = inflater.inflate(R.layout.fragment_create_collection, container, false)
        binding = FragmentCreateCollectionBinding.bind(mainView)

        val stateObserver = Observer<CreateCollectionViewModel.CreateCollectionState> {
            when (it) {
                CreateCollectionViewModel.CreateCollectionState.Loading -> {
                    binding.createCollectionProgressBar.show()
                }
                CreateCollectionViewModel.CreateCollectionState.Initial -> {
                    binding.createCollectionProgressBar.hide()
                    setImage()
                    setOnClickButtons()
                }
                is CreateCollectionViewModel.CreateCollectionState.Success -> {
                    binding.createCollectionProgressBar.hide()
                    callback?.backToCollectionsFragment()
                }
                is CreateCollectionViewModel.CreateCollectionState.Failure -> {
                    binding.createCollectionProgressBar.hide()
                    createErrorDialog(it.errorMessage)
                }
            }
        }
        viewModel.state.observe(viewLifecycleOwner, stateObserver)

        return binding.root
    }

    override fun onAttach(context: Context) {
        callback = activity as CreateCollectionListener
        super.onAttach(context)
    }

    private fun setImage() {
        if (args.iconId == 0) {
            binding.iconCollection.setImageResource(R.drawable.collection_icon_01)
        }
        else {
            binding.iconCollection.setImageResource(args.iconId)
        }
    }


    private fun setOnClickButtons() {
        setOnClickChooseIcon()
        setOnClickSaveButton()
        setOnClickBackButton()
    }

    private fun setOnClickChooseIcon() {
        binding.chooseIconButton.setOnClickListener {
            findNavController().navigate(R.id.action_createCollectionFragment_to_selectionFragment)
        }
    }

    private fun setOnClickSaveButton() {
        binding.saveCollectionButton.setOnClickListener {
            viewModel.postCollection(binding.nameCollectionsEditText.text.toString(), args.iconId)
        }
    }

    private fun setOnClickBackButton() {
        binding.backCollectionsButton.setOnClickListener {
            callback?.backToCollectionsFragment()
        }
    }

    private fun createErrorDialog(message: String) {
        val builder = AlertDialog.Builder(context)

        builder.setTitle(getString(R.string.error))
        builder.setMessage(message)
        builder.show()
    }
}