package com.example.cinema.domain.usecase.profile

import android.content.Context
import com.example.cinema.domain.repository.ProfileRepository
import com.example.cinema.domain.usecase.storage.GetTokenFromLocalStorageUseCase
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class PostAvatarUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(context: Context, image: File){
        val getTokenFromLocalStorageUseCase = GetTokenFromLocalStorageUseCase(context)
        val token = getTokenFromLocalStorageUseCase.execute()

        val body = image.asRequestBody("image/png".toMediaType())
        val filePart = MultipartBody.Part.createFormData("file", "image.png", body)

        return repository.postAvatar(token, filePart)
    }
}