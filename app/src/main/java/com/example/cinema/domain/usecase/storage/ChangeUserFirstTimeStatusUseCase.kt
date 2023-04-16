package com.example.cinema.domain.usecase.storage

import android.content.Context
import com.example.cinema.data.repository.IsUserFirstTimeRepositoryImpl

class ChangeUserFirstTimeStatusUseCase(private val context: Context) {

    private val isUserFirstTimeRepositoryImpl by lazy {
        IsUserFirstTimeRepositoryImpl(context)
    }

    fun execute() {
        isUserFirstTimeRepositoryImpl.changeFirstTimeStatus()
    }

}