package com.example.cinema.presentation.bottomnavigation.collections.detail

import android.app.AlertDialog
import android.content.Context
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
import com.example.cinema.data.remote.api.dto.MovieDto
import com.example.cinema.data.remote.database.entity.CollectionEntity
import com.example.cinema.databinding.FragmentCollectionDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CollectionDetailFragment : Fragment() {
    private lateinit var binding: FragmentCollectionDetailBinding
    private val viewModel: CollectionInfoViewModel by viewModels()

    private var callback: CollectionInfoListener? = null

    interface CollectionInfoListener {
        fun backToCollectionsFragment()
        fun getCollectionInfo(): CollectionEntity
        fun changeIcon(icon: Int)
        fun changeName(name: String)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mainView = inflater.inflate(R.layout.fragment_collection_detail, container, false)
        binding = FragmentCollectionDetailBinding.bind(mainView)

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
        viewModel.state.observe(viewLifecycleOwner, stateObserver)

        return binding.root
    }

    override fun onStart() {
        val collectionInfo = callback?.getCollectionInfo()!!
        viewModel.getInfo(collectionInfo)
        super.onStart()
    }

    override fun onAttach(context: Context) {
        callback = activity as CollectionInfoListener
        super.onAttach(context)
    }

    private fun setOnClickListeners(collectionInfo: CollectionEntity, isFavorite: Boolean) {
        setOnClickEditButton(collectionInfo, isFavorite)
        setOnClickBackButton()
    }

    private fun setOnClickEditButton(collectionInfo: CollectionEntity, isFavorite: Boolean) {
        if (isFavorite) {
            binding.editButton.isGone = true
            return
        }
        binding.editButton.setOnClickListener {
            navigateToEditCollectionFragment()
        }
    }

    private fun setOnClickBackButton() {
        binding.backCollectionsButton.setOnClickListener {
            callback?.backToCollectionsFragment()
        }
    }


    private fun addCollectionInfo(collectionInfo: List<MovieDto>) {
        val collectionInfoRecyclerView = binding.collectionInfoRecyclerView

        collectionInfoRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        collectionInfoRecyclerView.adapter =
            CollectionInfoRecyclerAdapter(
                collectionInfo
            )
    }

    private fun navigateToEditCollectionFragment() {
        findNavController().navigate(R.id.action_collectionDetailFragment_to_editCollectionFragment)
    }

    private fun createErrorDialog(message: String) {
        val builder = AlertDialog.Builder(context)

        builder.setTitle(getString(R.string.error))
        builder.setMessage(message)
        builder.show()
    }
}