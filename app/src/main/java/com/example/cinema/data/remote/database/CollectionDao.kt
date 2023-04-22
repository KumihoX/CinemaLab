package com.example.cinema.data.remote.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.cinema.data.remote.database.entity.CollectionEntity

@Dao
interface CollectionDao {
    @Insert
    fun insertCollection(collection: CollectionEntity)

    @Query("SELECT * FROM collection")
    fun getCollections(): List<CollectionEntity>

    @Query("UPDATE collection SET image_id = :imageId, name = :name WHERE id = :id")
    fun changeCollection(imageId: Int, name: String, id: String)

    @Query("DELETE FROM collection WHERE id = :id")
    fun deleteCollection(id: String)

    @Query("DELETE FROM collection")
    fun deleteAllCollections()
}