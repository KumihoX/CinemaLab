package com.example.cinema.data.remote.dto

data class EpisodeDto(
    val episodeId: String,
    val name: String,
    val description: String,
    val director: String,
    val stars: List<String>,
    val year: String,
    val images: List<String>,
    val runtime: Int,
    val preview: String,
    val filePath: String
)