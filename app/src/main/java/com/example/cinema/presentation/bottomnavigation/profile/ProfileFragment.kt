package com.example.cinema.presentation.bottomnavigation.profile

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.cinema.R
import com.example.cinema.databinding.FragmentProfileBinding
import com.example.cinema.domain.model.Movie
import com.example.cinema.presentation.movie.moviedetail.MovieDetailFragment
import com.github.dhaval2404.imagepicker.ImagePicker
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val viewModel: ProfileViewModel by viewModels()

    private var callback: ProfileListener? = null
    interface ProfileListener {
        fun logout()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mainView = inflater.inflate(R.layout.fragment_profile, container, false)
        binding = FragmentProfileBinding.bind(mainView)

        val stateObserver = Observer<ProfileViewModel.ProfileState> {
            when (it) {
                ProfileViewModel.ProfileState.Loading -> {
                    binding.profileProgressBar.show()
                    binding.profileGroup.isGone = true
                }
                is ProfileViewModel.ProfileState.Success -> {
                    binding.profileProgressBar.hide()
                    setOnButtonsClickListener()
                    setOnEditAvatarClickListener()
                    addAvatar(it.user.avatar.toString())
                    addName("${it.user.firstName} ${it.user.lastName}")
                    addEmail(it.user.email)
                    binding.profileGroup.isGone = false
                }
                is ProfileViewModel.ProfileState.Failure -> {
                    binding.profileProgressBar.hide()
                    binding.profileGroup.isGone = false
                    createErrorDialog(it.errorMessage)
                }
            }
        }
        viewModel.state.observe(viewLifecycleOwner, stateObserver)

        return binding.root
    }

    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val data = result.data

            if (data?.data != null) {
                val fileUri = data.data
                val file = fileUri?.path?.let { File(it) }
                viewModel.loadProfileImage(file!!)
                binding.userAvatar.setImageURI(fileUri)
            }
        }

    override fun onStart() {
        viewModel.getProfileData()
        super.onStart()
    }

    override fun onAttach(context: Context) {
        callback = activity as ProfileListener
        super.onAttach(context)
    }

    private fun setOnButtonsClickListener() {
        setOnDiscussionClickListener()
        setOnExitButtonClickListener()
    }

    private fun setOnExitButtonClickListener() {
        binding.logout.setOnClickListener {
            viewModel.logout()
            callback?.logout()
            findNavController().navigate(R.id.action_profile_to_authorizationActivity)
        }
    }

    private fun setOnDiscussionClickListener() {
        binding.discussion.setOnClickListener {
            findNavController().navigate(R.id.action_profile_to_chatsActivity)
        }
    }

    private fun setOnEditAvatarClickListener() {
        binding.change.setOnClickListener {
            ImagePicker.with(this)
                .cropSquare()
                .createIntent { intent ->
                    startForProfileImageResult.launch(intent)
                }
        }
    }

    private fun addAvatar(avatar: String) {
        val imageView = binding.userAvatar
        Glide.with(this).load(avatar).into(imageView)
    }

    private fun addName(name: String) {
        binding.userName.text = name
    }

    private fun addEmail(email: String) {
        binding.userEmail.text = email
    }

    private fun createErrorDialog(message: String) {
        val builder = AlertDialog.Builder(context)

        builder.setTitle(getString(R.string.error))
        builder.setMessage(message)
        builder.show()
    }
}