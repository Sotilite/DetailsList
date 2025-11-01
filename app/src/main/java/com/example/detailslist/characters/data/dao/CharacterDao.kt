package com.example.detailslist.characters.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.detailslist.characters.data.entity.CharacterDbEntity

@Dao
interface CharacterDao {
    @Query("SELECT * FROM CharacterDbEntity")
    suspend fun getAll(): List<CharacterDbEntity>

    @Insert
    suspend fun insert(driverDbEntity: CharacterDbEntity)

    @Query("DELETE FROM CharacterDbEntity WHERE name = :name")
    suspend fun deleteByName(name: String)

    @Query("SELECT * FROM CharacterDbEntity WHERE name = :name")
    suspend fun getCharacter(name: String): CharacterDbEntity?
}