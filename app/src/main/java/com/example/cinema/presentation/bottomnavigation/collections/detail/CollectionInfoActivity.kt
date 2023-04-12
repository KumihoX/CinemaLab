package com.example.cinema.presentation.bottomnavigation.collections.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.navArgs
import com.example.cinema.data.remote.api.dto.CollectionListItemDto
import com.example.cinema.data.remote.database.entity.CollectionEntity
import com.example.cinema.databinding.ActivityCollectionInfoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CollectionInfoActivity : AppCompatActivity(), CollectionDetailFragment.CollectionInfoListener {

    private lateinit var binding: ActivityCollectionInfoBinding
    private val args: CollectionInfoActivityArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCollectionInfoBinding.inflate(layoutInflater)


        setContentView(binding.root)
    }

    override fun backToCollectionsFragment() {
        finish()
    }

    override fun getCollectionInfo(): CollectionEntity {
        return args.collectionInfo
    }
}