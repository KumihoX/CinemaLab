package com.example.cinema.data.remote.api.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ChatDto(
    val chatId: String,
    val chatName: String
) : Parcelable
