package com.example.cinema.presentation.bottomnavigation.collections.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.navArgs
import com.example.cinema.data.remote.database.entity.CollectionEntity
import com.example.cinema.databinding.ActivityCollectionInfoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CollectionInfoActivity : AppCompatActivity(), CollectionDetailFragment.CollectionInfoListener {

    private lateinit var binding: ActivityCollectionInfoBinding
    private val args: CollectionInfoActivityArgs by navArgs()

    private lateinit var collectionId : String
    private lateinit var collectionName : String
    private var collectionIcon : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCollectionInfoBinding.inflate(layoutInflater)

        collectionId = args.collectionInfo.id
        collectionName = args.collectionInfo.name
        collectionIcon = args.collectionInfo.imageId

        setContentView(binding.root)
    }

    override fun backToCollectionsFragment() {
        finish()
    }

    override fun changeIcon(icon: Int) {
        collectionIcon = icon
    }

    override fun changeName(name: String) {
        collectionName = name
    }

    override fun getCollectionInfo(): CollectionEntity {
        return CollectionEntity(
            collectionName, collectionIcon, collectionId
        )
    }
}