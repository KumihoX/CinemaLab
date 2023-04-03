package com.example.cinema.domain.model

import com.example.cinema.R

enum class AgeEnum(
    val age: Int,
    val color: Int
) {
    Eighteen(R.string.eighteen, R.color.eighteen),
    Sixteen(R.string.sixteen, R.color.sixteen),
    Twelve(R.string.twelve, R.color.twelve),
    Six(R.string.six, R.color.six),
    Zero(R.string.zero, R.color.zero)
}