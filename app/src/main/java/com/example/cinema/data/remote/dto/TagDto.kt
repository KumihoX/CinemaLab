package com.example.cinema.data.remote.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
@Parcelize
data class TagDto(
    val tagId: String,
    val tagName: String,
    val categoryName: String
) : Parcelable