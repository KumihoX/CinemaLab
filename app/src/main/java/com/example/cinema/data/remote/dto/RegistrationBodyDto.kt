package com.example.cinema.data.remote.dto

data class RegistrationBodyDto(
    val email: String,
    val firstName: String,
    val lastName: String,
    val password: String
)