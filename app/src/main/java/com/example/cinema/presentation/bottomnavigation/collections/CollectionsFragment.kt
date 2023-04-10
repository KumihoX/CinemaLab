package com.example.cinema.presentation.bottomnavigation.collections

import android.app.AlertDialog
import android.content.Intent
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
import com.example.cinema.R
import com.example.cinema.data.remote.dto.CollectionListItemDto
import com.example.cinema.databinding.FragmentCollectionsBinding
import com.example.cinema.domain.model.Movie
import com.example.cinema.presentation.bottomnavigation.collections.detail.CollectionInfoActivity
import com.example.cinema.presentation.bottomnavigation.main.MainFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CollectionsFragment : Fragment() {
    private lateinit var binding: FragmentCollectionsBinding
    private val viewModel: CollectionsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mainView = inflater.inflate(R.layout.fragment_collections, container, false)
        binding = FragmentCollectionsBinding.bind(mainView)

        val stateObserver = Observer<CollectionsViewModel.CollectionsState> {
            when (it) {
                CollectionsViewModel.CollectionsState.Loading -> {
                    binding.collectionsProgressBar.show()
                    binding.collectionsGroup.isGone = true
                }
                is CollectionsViewModel.CollectionsState.Success -> {
                    binding.collectionsProgressBar.hide()
                    binding.collectionsGroup.isGone = false
                    setOnClickOnAddButton()
                    addCollections(it.collections)
                }
                is CollectionsViewModel.CollectionsState.Failure -> {
                    binding.collectionsProgressBar.hide()
                    binding.collectionsGroup.isGone = false
                    createErrorDialog(it.errorMessage)
                }
            }
        }
        viewModel.state.observe(viewLifecycleOwner, stateObserver)

        return binding.root
    }

    override fun onStart() {
        viewModel.getCollections()
        super.onStart()
    }

    private fun setOnClickOnAddButton() {
        binding.addButton.setOnClickListener {
            findNavController().navigate(R.id.action_collection_to_createCollectionActivity)
        }
    }

    private fun addCollections(collections: List<CollectionListItemDto>) {
        val collectionsRecyclerView = binding.collectionsRecyclerView

        collectionsRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        collectionsRecyclerView.adapter =
            CollectionsRecyclerAdapter(
                collections
            ) { navigateToCollectionInfoActivity(it) }
    }

    private fun navigateToCollectionInfoActivity(collectionInfo: CollectionListItemDto) {
        val action = CollectionsFragmentDirections.actionCollectionToCollectionInfoActivity(collectionInfo)
        findNavController().navigate(action)
    }

    private fun createErrorDialog(message: String) {
        val builder = AlertDialog.Builder(context)

        builder.setTitle(getString(R.string.error))
        builder.setMessage(message)
        builder.show()
    }
}