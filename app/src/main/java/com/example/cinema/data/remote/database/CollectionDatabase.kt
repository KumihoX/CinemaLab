package com.example.cinema.data.remote.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cinema.data.remote.database.entity.CollectionEntity

@Database(entities = [CollectionEntity::class], version = 1)
abstract class CollectionDatabase : RoomDatabase() {
    abstract val collectionDao: CollectionDao
}