package com.example.cinema.data.remote.api.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MessageDto(
    val messageId: String,
    val creationDateTime: String,
    val authorId: String,
    val authorName: String,
    val authorAvatar: String? = "",
    val text: String
) : Parcelable