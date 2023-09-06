package com.example.clase19mapsmvvm.model.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.clase19mapsmvvm.model.data.Terremoto

@Dao
interface TerremotoDao {

    @Query("SELECT * FROM terremoto")
    fun getAllTerremotos(): MutableList<Terremoto>

    @Query("SELECT * FROM terremoto WHERE magnitude > :mag")
    fun getTerremotosByMagnitude(mag: String) : MutableList<Terremoto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(terremotos: MutableList<Terremoto>)

    @Update
    fun update(terremoto: Terremoto)

    @Delete
    fun delete(terremoto: Terremoto)
}