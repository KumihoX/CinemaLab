package com.example.cinema.domain.model

import android.os.Parcelable
import com.example.cinema.R
import kotlinx.android.parcel.Parcelize

@Parcelize
enum class AgeEnum(
    val age: Int,
    val color: Int
) : Parcelable {
    Eighteen(R.string.eighteen, R.color.eighteen),
    Sixteen(R.string.sixteen, R.color.sixteen),
    Twelve(R.string.twelve, R.color.twelve),
    Six(R.string.six, R.color.six),
    Zero(R.string.zero, R.color.zero)
}