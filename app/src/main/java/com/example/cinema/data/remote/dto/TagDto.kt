package com.example.cinema.data.remote.dto

import java.io.Serializable

data class TagDto(
    val tagId: String,
    val tagName: String,
    val categoryName: String
) : Serializable