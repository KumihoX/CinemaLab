package com.example.cinema.data.remote.dto

import java.io.Serializable

data class MovieDto(
    val movieId: String,
    val name: String,
    val description: String,
    val age: String,
    val chatInfo: ChatDto? = null,
    val imageUrls: List<String>,
    val poster: String,
    val tags: List<TagDto>
) : Serializable
