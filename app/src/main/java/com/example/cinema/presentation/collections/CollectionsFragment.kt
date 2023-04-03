package com.example.cinema.presentation.collections

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cinema.R
import com.example.cinema.data.remote.dto.CollectionListItemDto
import com.example.cinema.databinding.FragmentCollectionsBinding
import com.example.cinema.databinding.FragmentProfileBinding
import com.example.cinema.domain.model.Movie
import com.example.cinema.presentation.main.CustomRecyclerAdapter
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

        setOnClickOnAddButton()
        getCollections()

        return binding.root
    }

    private fun setOnClickOnAddButton() {
        binding.addButton.setOnClickListener {
            findNavController().navigate(R.id.action_collection_to_collectionsActivity2)
        }
    }

    private fun getCollections() {
        val collectionsListObserver = Observer<List<CollectionListItemDto>> { newList ->
            if (newList.isNotEmpty()) {
                addCollections(newList)
            }
        }
        viewModel.collectionsList.observe(viewLifecycleOwner, collectionsListObserver)
    }

    private fun addCollections(collections: List<CollectionListItemDto>) {
        val collectionsRecyclerView = binding.collectionsRecyclerView

        collectionsRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        collectionsRecyclerView.adapter =
            CollectionsRecyclerAdapter(
                collections
            )
    }

}