package com.example.cinema.presentation.bottomnavigation.collections.change.selection

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.cinema.R
import com.example.cinema.databinding.FragmentSelectionBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectionFragment : Fragment() {
    private lateinit var binding: FragmentSelectionBinding
    private val viewModel: SelectionViewModel by viewModels()
    private val iconsList = mutableListOf<Int>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mainView = inflater.inflate(R.layout.fragment_selection, container, false)
        binding = FragmentSelectionBinding.bind(mainView)

        val stateObserver = Observer<SelectionViewModel.SelectionState> {
            when (it) {
                SelectionViewModel.SelectionState.Loading -> {
                    binding.selectionProgressBar.show()
                    binding.selectionGroup.isGone = true
                }
                is SelectionViewModel.SelectionState.Success -> {
                    binding.selectionProgressBar.hide()
                    binding.selectionGroup.isGone = false
                }
                is SelectionViewModel.SelectionState.Failure -> {
                    binding.selectionProgressBar.hide()
                    binding.selectionGroup.isGone = false
                    createErrorDialog(it.errorMessage)
                }
            }
        }
        viewModel.state.observe(viewLifecycleOwner, stateObserver)

        return binding.root
    }

    override fun onStart() {
        createIconRecyclerView()
        setOnBackButtonClickListener()

        viewModel.changeStateOnSuccess()

        super.onStart()
    }

    private fun createIconRecyclerView() {
        val selectIconRecyclerView = binding.selectIconRecyclerView
        selectIconRecyclerView.setHasFixedSize(true)

        selectIconRecyclerView.layoutManager =
            StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL)

        addToList()

        selectIconRecyclerView.adapter =
            SelectionRecyclerAdapter(iconsList) {navigateToCreateCollectionFragment(it)}
    }

    private fun setOnBackButtonClickListener() {
        binding.backChooseIconButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun navigateToCreateCollectionFragment(iconId: Int) {
        val action =
            SelectionFragmentDirections.actionSelectionFragmentToCreateCollectionFragment(iconId)
        findNavController().navigate(action)
    }

    private fun addToList() {
        iconsList.add(R.drawable.collection_icon_01)
        iconsList.add(R.drawable.collection_icon_02)
        iconsList.add(R.drawable.collection_icon_03)
        iconsList.add(R.drawable.collection_icon_04)
        iconsList.add(R.drawable.collection_icon_05)
        iconsList.add(R.drawable.collection_icon_06)
        iconsList.add(R.drawable.collection_icon_07)
        iconsList.add(R.drawable.collection_icon_08)
        iconsList.add(R.drawable.collection_icon_09)
        iconsList.add(R.drawable.collection_icon_10)
        iconsList.add(R.drawable.collection_icon_11)
        iconsList.add(R.drawable.collection_icon_12)
        iconsList.add(R.drawable.collection_icon_13)
        iconsList.add(R.drawable.collection_icon_14)
        iconsList.add(R.drawable.collection_icon_15)
        iconsList.add(R.drawable.collection_icon_16)
        iconsList.add(R.drawable.collection_icon_17)
        iconsList.add(R.drawable.collection_icon_18)
        iconsList.add(R.drawable.collection_icon_19)
        iconsList.add(R.drawable.collection_icon_20)
        iconsList.add(R.drawable.collection_icon_21)
        iconsList.add(R.drawable.collection_icon_22)
        iconsList.add(R.drawable.collection_icon_23)
        iconsList.add(R.drawable.collection_icon_24)
        iconsList.add(R.drawable.collection_icon_25)
        iconsList.add(R.drawable.collection_icon_26)
        iconsList.add(R.drawable.collection_icon_27)
        iconsList.add(R.drawable.collection_icon_28)
        iconsList.add(R.drawable.collection_icon_29)
        iconsList.add(R.drawable.collection_icon_30)
        iconsList.add(R.drawable.collection_icon_31)
        iconsList.add(R.drawable.collection_icon_32)
        iconsList.add(R.drawable.collection_icon_33)
        iconsList.add(R.drawable.collection_icon_34)
        iconsList.add(R.drawable.collection_icon_35)
        iconsList.add(R.drawable.collection_icon_36)
    }

    private fun createErrorDialog(message: String) {
        val builder = AlertDialog.Builder(context)

        builder.setTitle(getString(R.string.error))
        builder.setMessage(message)
        builder.show()
    }
}