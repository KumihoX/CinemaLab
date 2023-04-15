package com.example.cinema.presentation.bottomnavigation.collections.change.edit

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
import com.example.cinema.R
import com.example.cinema.databinding.FragmentEditCollectionBinding
import com.example.cinema.presentation.bottomnavigation.collections.CollectionsFragmentDirections
import com.example.cinema.presentation.bottomnavigation.collections.detail.CollectionDetailFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditCollectionFragment : Fragment() {

    private lateinit var binding: FragmentEditCollectionBinding
    private val viewModel: EditCollectionViewModel by viewModels()

    private var callback: CollectionDetailFragment.CollectionInfoListener? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mainView = inflater.inflate(R.layout.fragment_edit_collection, container, false)
        binding = FragmentEditCollectionBinding.bind(mainView)

        val stateObserver = Observer<EditCollectionViewModel.EditCollectionState> {
            when (it) {
                EditCollectionViewModel.EditCollectionState.Loading -> {
                    binding.editCollectionProgressBar.show()
                }
                EditCollectionViewModel.EditCollectionState.Initial -> {
                    binding.editCollectionProgressBar.hide()
                    setOnClickButtons()
                    setCollectionName()
                    setCollectionIcon()
                }
                is EditCollectionViewModel.EditCollectionState.Success -> {
                    binding.editCollectionProgressBar.hide()
                    callback?.backToCollectionsFragment()
                }
                is EditCollectionViewModel.EditCollectionState.Failure -> {
                    binding.editCollectionProgressBar.hide()
                    createErrorDialog(it.errorMessage)
                }
            }
        }
        viewModel.state.observe(viewLifecycleOwner, stateObserver)

        return binding.root
    }

    override fun onAttach(context: Context) {
        callback = activity as CollectionDetailFragment.CollectionInfoListener
        super.onAttach(context)
    }

    private fun setCollectionName() {
        binding.nameCollectionsEditText.setText(callback?.getCollectionInfo()!!.name)
    }

    private fun setCollectionIcon() {
        binding.iconCollection.setImageResource(callback?.getCollectionInfo()!!.imageId)
    }

    private fun setOnClickButtons() {
        setOnBackClickListener()
        setOnSaveButtonClickListener()
        setOnDeleteButtonClickListener()
        setOnChooseIconButtonClickListener()
    }

    private fun setOnBackClickListener() {
        binding.backCollectionsButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setOnSaveButtonClickListener() {
        binding.saveCollectionButton.setOnClickListener {
            viewModel.saveChanges(
                callback?.getCollectionInfo()!!.id,
                binding.nameCollectionsEditText.text.toString(),
                callback?.getCollectionInfo()!!.imageId
            )
            findNavController().popBackStack()
        }
    }

    private fun setOnDeleteButtonClickListener() {
        binding.deleteButton.setOnClickListener {
            viewModel.deleteCollection(collectionId = callback?.getCollectionInfo()!!.id)
        }
    }

    private fun setOnChooseIconButtonClickListener() {
        binding.chooseIconButton.setOnClickListener {
            callback?.changeName(binding.nameCollectionsEditText.text.toString())
            findNavController().navigate(R.id.action_editCollectionFragment_to_selectionFragment)
        }
    }

    private fun createErrorDialog(message: String) {
        val builder = AlertDialog.Builder(context)

        builder.setTitle(getString(R.string.error))
        builder.setMessage(message)
        builder.show()
    }
}