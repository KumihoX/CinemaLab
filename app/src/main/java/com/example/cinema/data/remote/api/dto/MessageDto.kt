package com.example.cinema.data.remote.api.dto

data class MessageDto(
    val messageId: String,
    val creationDateTime: String,
    val authorId: String,
    val authorName: String,
    val authorAvatar: String? = "",
    val text: String
)