package com.example.cinema.domain.model

import com.example.cinema.data.remote.dto.ChatDto
import com.example.cinema.data.remote.dto.TagDto

data class Movie(
    val movieId: String,
    val name: String,
    val description: String,
    val age: AgeEnum,
    val chatInfo: ChatDto? = null,
    val imageUrls: List<String>,
    val poster: String,
    val tags: List<TagDto>
) : java.io.Serializable
