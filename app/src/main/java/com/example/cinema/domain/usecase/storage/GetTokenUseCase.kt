package com.example.cinema.domain.usecase.storage

import android.content.Context
import com.example.cinema.data.remote.api.dto.AuthTokenPairDto
import javax.inject.Inject

class GetTokenUseCase @Inject constructor(
) {
    operator fun invoke(context: Context): AuthTokenPairDto {
        val getTokenFromLocalStorageUseCase = GetTokenFromLocalStorageUseCase(context)
        return getTokenFromLocalStorageUseCase.execute()
    }
}