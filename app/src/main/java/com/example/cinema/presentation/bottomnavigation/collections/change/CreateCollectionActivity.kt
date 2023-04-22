package com.example.cinema.presentation.bottomnavigation.collections.change

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.navArgs
import com.example.cinema.R
import com.example.cinema.data.remote.database.entity.CollectionEntity
import com.example.cinema.databinding.ActivityCreateCollectionBinding
import com.example.cinema.presentation.bottomnavigation.collections.detail.CollectionDetailFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateCollectionActivity : AppCompatActivity(),
    CollectionDetailFragment.CollectionInfoListener {

    private lateinit var binding: ActivityCreateCollectionBinding
    private val args: CreateCollectionActivityArgs by navArgs()

    private lateinit var collectionId: String
    private lateinit var collectionName: String
    private var collectionIcon: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateCollectionBinding.inflate(layoutInflater)

        setTheme(R.style.Theme_Cinema)

        collectionId = args.collectionInfo.id
        collectionName = args.collectionInfo.name
        collectionIcon = args.collectionInfo.imageId
        setContentView(binding.root)
    }


    override fun backToCollectionsFragment() {
        finish()
    }

    override fun getCollectionInfo(): CollectionEntity {
        return CollectionEntity(
            collectionId, collectionIcon, collectionName
        )
    }

    override fun changeIcon(icon: Int) {
        collectionIcon = icon
    }

    override fun changeName(name: String) {
        collectionName = name
    }
}