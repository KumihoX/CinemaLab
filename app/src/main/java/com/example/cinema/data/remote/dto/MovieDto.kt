package com.example.cinema.data.remote.dto

import com.example.cinema.domain.model.AgeEnum
import com.example.cinema.domain.model.Movie
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

fun MovieDto.toMovie(): Movie {
    val ageType = when (age) {
        "18+" -> AgeEnum.Eighteen
        "16+" -> AgeEnum.Sixteen
        "12+" -> AgeEnum.Twelve
        "6+" -> AgeEnum.Six
        "0+" -> AgeEnum.Zero
        else -> AgeEnum.Twelve
    }

    return Movie(
        movieId = movieId,
        description = description,
        age = ageType,
        chatInfo = chatInfo,
        imageUrls = imageUrls,
        poster = poster,
        tags = tags
    )
}