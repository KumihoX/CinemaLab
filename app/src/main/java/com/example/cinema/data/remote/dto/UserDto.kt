package com.example.cinema.data.remote.dto

data class UserDto(
    val userId: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val avatar: String? = ""
)
