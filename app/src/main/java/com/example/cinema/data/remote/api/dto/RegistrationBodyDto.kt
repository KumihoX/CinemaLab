package com.example.cinema.data.remote.api.dto

data class RegistrationBodyDto(
    val email: String,
    val firstName: String,
    val lastName: String,
    val password: String
)