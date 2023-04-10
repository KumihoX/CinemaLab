package com.example.cinema.presentation.bottomnavigation.collections.change

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cinema.databinding.ActivityCreateCollectionBinding
import com.example.cinema.presentation.bottomnavigation.collections.change.create.CreateCollectionFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateCollectionActivity : AppCompatActivity(),
    CreateCollectionFragment.CreateCollectionListener {

    private lateinit var binding: ActivityCreateCollectionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateCollectionBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }


    override fun backToCollectionsFragment() {
        finish()
    }
}