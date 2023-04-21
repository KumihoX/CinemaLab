package com.example.cinema.data.remote.api.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
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
) : Parcelable