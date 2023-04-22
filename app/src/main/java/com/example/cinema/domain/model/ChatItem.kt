package com.example.cinema.domain.model

data class ChatItem(
    val type: String,
    val text: String,
    val author: String? = "",
    val authorAvatar: String? = "",
    var avatarIsVisible: Boolean? = false,
    val creationTime: String? = "",
    val firstMessage: Boolean? = true,
)