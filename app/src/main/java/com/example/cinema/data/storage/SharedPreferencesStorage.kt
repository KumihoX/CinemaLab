package com.example.cinema.data.storage

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.cinema.data.remote.dto.AuthTokenPairDto
import com.example.cinema.data.remote.dto.CollectionListItemDto

class SharedPreferencesStorage(context: Context) : TokenStorage, FavoritesStorage {

    companion object {
        const val ENCRYPTED_SHARED_PREFS_NAME = "encryptedSharedPreferences"
    }

    private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

    private val sharedPreferences = EncryptedSharedPreferences.create(
        ENCRYPTED_SHARED_PREFS_NAME,
        masterKeyAlias,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )


    override fun saveToken(token: AuthTokenPairDto) {
        sharedPreferences.edit()
            .putString(TokenStorage.TOKEN_KEY, token.accessToken)
            .putInt(TokenStorage.TOKEN_KEY_TIME, token.accessTokenExpiresIn)
            .putString(TokenStorage.REFRESH_TOKEN_KEY, token.refreshToken)
            .putInt(TokenStorage.REFRESH_TOKEN_KEY_TIME, token.refreshTokenExpiresIn)
            .apply()
    }

    override fun getToken(): AuthTokenPairDto {
        return AuthTokenPairDto(
            sharedPreferences.getString(
                TokenStorage.TOKEN_KEY, ""
            ).toString(),
            sharedPreferences.getInt(
                TokenStorage.TOKEN_KEY_TIME, 0
            ),
            sharedPreferences.getString(
                TokenStorage.REFRESH_TOKEN_KEY, ""
            ).toString(),
            sharedPreferences.getInt(
                TokenStorage.REFRESH_TOKEN_KEY_TIME, 0
            )
        )
    }

    override fun deleteToken() {
        sharedPreferences.edit()
            .remove(TokenStorage.TOKEN_KEY)
            .remove(TokenStorage.TOKEN_KEY_TIME)
            .remove(TokenStorage.REFRESH_TOKEN_KEY)
            .remove(TokenStorage.REFRESH_TOKEN_KEY_TIME)
            .apply()
    }

    override fun saveFavoriteCollection(collection: CollectionListItemDto) {
        sharedPreferences.edit()
            .putString(FavoritesStorage.FAVORITE_COLLECTION_ID, collection.collectionId)
            .putString(FavoritesStorage.FAVORITE_COLLECTION_NAME, collection.name)
            .apply()
    }

    override fun getFavoriteCollection(): CollectionListItemDto {
        return CollectionListItemDto(
            sharedPreferences.getString(
                FavoritesStorage.FAVORITE_COLLECTION_ID, ""
            ).toString(),
            sharedPreferences.getString(
                FavoritesStorage.FAVORITE_COLLECTION_NAME, ""
            ).toString()
        )
    }
}