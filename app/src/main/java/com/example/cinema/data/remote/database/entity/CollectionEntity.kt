package com.example.cinema.data.remote.database.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "collection")
data class CollectionEntity(
    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "image_id")
    val imageId: Int,

    @ColumnInfo(name = "id")
    @PrimaryKey val id: String = ""
) : Parcelable