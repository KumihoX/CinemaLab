package com.example.cinema.data.remote.api.dto

data class EpisodeViewDto(
    val episodeId: String,
    val movieId: String,
    val episodeName: String,
    val movieName: String,
    val preview: String,
    val filePath: String,
    val time: Int
)
