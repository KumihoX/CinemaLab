package com.example.cinema.domain.model

import android.os.Parcelable
import com.example.cinema.data.remote.api.dto.ChatDto
import com.example.cinema.data.remote.api.dto.TagDto
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val movieId: String,
    val name: String,
    val description: String,
    val age: AgeEnum,
    val chatInfo: ChatDto? = null,
    val imageUrls: List<String>,
    val poster: String,
    val tags: List<TagDto>
) : Parcelable
