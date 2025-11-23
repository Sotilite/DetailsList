package com.example.detailslist.di

import android.content.Context
import androidx.room.Room
import com.example.detailslist.characters.data.db.CharactersDatabase
import org.koin.dsl.module

val dbModule = module {
    single { DatabaseBuilder.getInstance(get()) }
}

object DatabaseBuilder {
    private var INSTANCE: CharactersDatabase? = null

    fun getInstance(context: Context) = buildRoomDB(context)

    private fun buildRoomDB(context: Context) =
        Room.databaseBuilder(
            context.applicationContext,
            CharactersDatabase::class.java,
            "characters"
        ).build()
}
