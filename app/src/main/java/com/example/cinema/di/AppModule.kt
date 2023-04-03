package com.example.cinema.di

import com.example.cinema.common.Constants
import com.example.cinema.data.remote.AuthApi
import com.example.cinema.data.remote.CoverApi
import com.example.cinema.data.remote.MovieApi
import com.example.cinema.data.remote.ProfileApi
import com.example.cinema.data.repository.AuthRepositoryImpl
import com.example.cinema.data.repository.CoverRepositoryImpl
import com.example.cinema.data.repository.MovieRepositoryImpl
import com.example.cinema.data.repository.ProfileRepositoryImpl
import com.example.cinema.domain.repository.AuthRepository
import com.example.cinema.domain.repository.CoverRepository
import com.example.cinema.domain.repository.MovieRepository
import com.example.cinema.domain.repository.ProfileRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private val client = OkHttpClient.Builder().apply {
        connectTimeout(15, TimeUnit.SECONDS)
        readTimeout(60, TimeUnit.SECONDS)
        writeTimeout(60, TimeUnit.SECONDS)

        val logLevel = HttpLoggingInterceptor.Level.BODY
        addInterceptor(HttpLoggingInterceptor().setLevel(logLevel))
    }

    @Provides
    @Singleton
    fun provideAuthApi(): AuthApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client.build())
            .build()
            .create(AuthApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(api: AuthApi): AuthRepository {
        return AuthRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideCoverApi(): CoverApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client.build())
            .build()
            .create(CoverApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCoverRepository(api: CoverApi): CoverRepository {
        return CoverRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideProfileApi(): ProfileApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client.build())
            .build()
            .create(ProfileApi::class.java)
    }

    @Provides
    @Singleton
    fun provideProfileRepository(api: ProfileApi): ProfileRepository {
        return ProfileRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideMovieApi(): MovieApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client.build())
            .build()
            .create(MovieApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMovieRepository(api: MovieApi): MovieRepository {
        return MovieRepositoryImpl(api)
    }
}