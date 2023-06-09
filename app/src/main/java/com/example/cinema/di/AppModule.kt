package com.example.cinema.di

import android.content.Context
import androidx.room.Room
import com.example.cinema.common.Constants
import com.example.cinema.common.Constants.DATABASE_NAME
import com.example.cinema.data.remote.*
import com.example.cinema.data.remote.api.*
import com.example.cinema.data.remote.database.CollectionDatabase
import com.example.cinema.data.repository.*
import com.example.cinema.data.websocket.ChatsWebSocket
import com.example.cinema.data.websocket.ChatsWebSocketListener
import com.example.cinema.domain.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Provides
    @Singleton
    fun provideCollectionApi(): CollectionsApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client.build())
            .build()
            .create(CollectionsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCollectionRepository(api: CollectionsApi): CollectionRepository {
        return CollectionRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideChatsApi(): ChatsApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client.build())
            .build()
            .create(ChatsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideChatsRepository(api: ChatsApi): ChatsRepository {
        return ChatsRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideEpisodesApi(): EpisodesApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client.build())
            .build()
            .create(EpisodesApi::class.java)
    }

    @Provides
    @Singleton
    fun provideEpisodesRepository(api: EpisodesApi): EpisodesRepository {
        return EpisodesRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideHistoryApi(): HistoryApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client.build())
            .build()
            .create(HistoryApi::class.java)
    }

    @Provides
    @Singleton
    fun provideHistoryRepository(api: HistoryApi): HistoryRepository {
        return HistoryRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        CollectionDatabase::class.java,
        DATABASE_NAME
    ).build()

    @Provides
    @Singleton
    fun provideDao(database: CollectionDatabase) = database.collectionDao

    @Provides
    @Singleton
    fun provideChatsWebSocketListener(): ChatsWebSocketListener {
        return ChatsWebSocketListener()
    }

    @Provides
    @Singleton
    fun provideChatsWebSocket(chatsWebSocketListener: ChatsWebSocketListener): ChatsWebSocket {
        return ChatsWebSocket(client.build(), chatsWebSocketListener)
    }

    @Provides
    @Singleton
    fun provideChatsWebSocketRepository(chatsWebSocket: ChatsWebSocket): ChatsWebSocketRepository {
        return ChatsWebSocketRepositoryImpl(chatsWebSocket)
    }
}
