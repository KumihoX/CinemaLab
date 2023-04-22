package com.example.cinema.data.repository

import android.content.Context
import com.example.cinema.data.storage.SharedPreferencesStorage
import com.example.cinema.domain.repository.IsUserFirstTimeRepository

class IsUserFirstTimeRepositoryImpl(context: Context) : IsUserFirstTimeRepository {
    private val sharedPreferencesStorage by lazy {
        SharedPreferencesStorage(context)
    }

    override fun changeFirstTimeStatus() {
        sharedPreferencesStorage.changeFirstTimeStatus()
    }

    override fun getFirstTimeStatus(): Boolean {
        return sharedPreferencesStorage.getFirstTimeStatus()
    }
}