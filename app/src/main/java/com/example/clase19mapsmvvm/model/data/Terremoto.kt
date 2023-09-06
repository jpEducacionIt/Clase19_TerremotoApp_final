package com.example.clase19mapsmvvm.model.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "terremoto")
data class Terremoto(
    @PrimaryKey val id: String,
    val place: String,
    val magnitude: String,
    val time: String,
    @ColumnInfo(name = "long") val longitud: String,
    val lat: String
): Parcelable
