package com.example.cinema.domain.repository

interface IsUserFirstTimeRepository {
    fun changeFirstTimeStatus()

    fun getFirstTimeStatus(): Boolean
}