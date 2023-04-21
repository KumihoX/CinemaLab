package com.example.cinema.data.remote.api.dto

import java.io.Serializable

data class CollectionListItemDto(
    val collectionId: String,
    val name: String
) : Serializable