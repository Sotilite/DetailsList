package com.example.detailslist.characters.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.detailslist.characters.data.dao.CharacterDao
import com.example.detailslist.characters.data.entity.CharacterDbEntity

@Database(entities = [CharacterDbEntity::class], version = 1)
abstract class CharactersDatabase: RoomDatabase() {
    abstract fun characterDao(): CharacterDao
}