package com.example.cinema.data.storage

interface IsUserFirstTimeStorage {
    companion object {
        const val FIRST_TIME_STATUS = "firstTimeStatus"
    }

    fun changeFirstTimeStatus()

    fun getFirstTimeStatus(): Boolean
}